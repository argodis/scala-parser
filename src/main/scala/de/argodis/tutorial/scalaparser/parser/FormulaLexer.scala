package de.argodis.tutorial.scalaparser.parser

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers
import de.argodis.tutorial.scalaparser.parser.tokens._

object FormulaLexer extends RegexParsers {
  override def skipWhitespace = true
  override val whiteSpace: Regex = "[ \t\r\f]+".r

  // Braces
  def brace_left: Parser[FormulaToken] = "(" ^^ { _ => BRACE_LEFT }
  def brace_right: Parser[FormulaToken] = ")" ^^ { _ => BRACE_RIGHT }
  // Algebraic operators
  def operator_multiply: Parser[FormulaToken] = "*" ^^ { _ => OPERATOR_MULTIPLY }
  def operator_divide: Parser[FormulaToken] = "/" ^^ { _ => OPERATOR_DIVIDE }
  def operator_add: Parser[FormulaToken] = "+" ^^ { _ => OPERATOR_ADD }
  def operator_subtract: Parser[FormulaToken] = "-" ^^ { _ => OPERATOR_SUBTRACT }
  // Variable
  def variable: Parser[FormulaToken] = "$" ~> """\d+""".r ^^ { id => VARIABLE(id.toInt) }
  // Constant
  def constant: Parser[FormulaToken] = {
    """-?(\d+(\.\d*)?|\d*\.\d+)([eE][+-]?\d+)?[fFdD]?""".r ^^ { value => CONSTANT(value.toDouble) }
  }

  def tokens: Parser[List[FormulaToken]] =
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
}
