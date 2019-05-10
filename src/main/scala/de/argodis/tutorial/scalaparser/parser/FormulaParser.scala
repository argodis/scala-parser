package de.argodis.tutorial.scalaparser.parser

import de.argodis.tutorial.scalaparser.parser.nodes.{Constant, FormulaAST, Variable}
import de.argodis.tutorial.scalaparser.parser.tokens.{CONSTANT, FormulaToken, VARIABLE}

import scala.util.parsing.combinator.Parsers

object FormulaParser extends Parsers {
  // This is required for the token reader
  override type Elem = FormulaToken

  // Terminal variables
  private def constant = accept("Constant", { case CONSTANT(value) => Constant(value) })
  private def variable = accept("Variable", { case VARIABLE(id) => Variable(id) })
  private def terminal: Parser[FormulaAST] = constant | variable

  //
  private def formula: Parser[FormulaAST] = phrase(terminal)

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
