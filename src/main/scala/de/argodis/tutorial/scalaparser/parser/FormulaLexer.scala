package de.argodis.tutorial.scalaparser.parser

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers
import de.argodis.tutorial.scalaparser.parser.tokens._

object FormulaLexer extends RegexParsers {
  override def skipWhitespace = true
  override val whiteSpace: Regex = "[ \t\r\f]+".r

  // Braces
  private def brace_left: Parser[FormulaToken] = "(" ^^ { _ => BRACE_LEFT }
  private def brace_right: Parser[FormulaToken] = ")" ^^ { _ => BRACE_RIGHT }
  // Algebraic operators
  private def operator_multiply: Parser[FormulaToken] = "*" ^^ { _ => OPERATOR_MULTIPLY }
  private def operator_divide: Parser[FormulaToken] = "/" ^^ { _ => OPERATOR_DIVIDE }
  private def operator_add: Parser[FormulaToken] = "+" ^^ { _ => OPERATOR_ADD }
  private def operator_subtract: Parser[FormulaToken] = "-" ^^ { _ => OPERATOR_SUBTRACT }
  // Variable
  private def variable: Parser[FormulaToken] = "$" ~> """\d+""".r ^^ { id => VARIABLE(id.toInt) }
  // Constant
  private def constant: Parser[FormulaToken] = {
    """-?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?[fFdD]?""".r ^^ { value => CONSTANT(value.toDouble) }
  }

  private def tokens: Parser[List[FormulaToken]] =
    phrase(
      rep1(
        brace_left
          | variable
          | constant
          | brace_right
          | operator_add
          | operator_subtract
          | operator_multiply
          | operator_divide
    )) ^^ { rawTokens => rawTokens }

  def tokenize(formula: String): ParseResult[List[FormulaToken]] = parse(tokens, formula)
  def tokenize2(formula: String): Either[String, List[FormulaToken]] = parse(tokens, formula) match {
    case Success(tokens, _ ) => Right(tokens)
    case Failure(msg, _) => Left(msg)
  }
}
