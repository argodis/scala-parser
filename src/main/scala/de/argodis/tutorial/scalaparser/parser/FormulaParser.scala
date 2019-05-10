package de.argodis.tutorial.scalaparser.parser

import de.argodis.tutorial.scalaparser.parser.nodes.{Constant, FormulaAST}
import de.argodis.tutorial.scalaparser.parser.tokens.{CONSTANT, FormulaToken}
import scala.util.parsing.combinator.Parsers

object FormulaParser extends Parsers {
  // This is required for the token reader
  override type Elem = FormulaToken

  private def constant = accept("Constant", { case CONSTANT(value) => Constant(value) })
  private def formula: Parser[FormulaAST] = phrase(constant)

  def parse(formulaExpression: String): Either[String, FormulaAST] =
    // Unfortunately ParseResult does not provide flatMap
    FormulaLexer.tokenize(formulaExpression) match {
      case FormulaLexer.Success(tokens, _) =>
        formula(FormulaTokenReader(tokens.asInstanceOf[List[FormulaToken]])) match {
          case Success(formulaAST, _) => Right(formulaAST)
          case Failure(msg, _) => Left(msg)
          case Error(msg, _) => Left(msg)
        }
      case FormulaLexer.Failure(msg, _) => Left(msg)
      case FormulaLexer.Error(msg, _) => Left(msg)
    }
}
