package de.argodis.tutorial.scalaparser.parser

import de.argodis.tutorial.scalaparser.parser.nodes.{Constant, FormulaAST, OperatorAdd, OperatorDivide, OperatorMultiply, OperatorSubtract, Variable}
import de.argodis.tutorial.scalaparser.parser.tokens.{CONSTANT, FormulaToken, OPERATOR_ADD, OPERATOR_DIVIDE, OPERATOR_MULTIPLY, OPERATOR_SUBTRACT, VARIABLE}

import scala.util.parsing.combinator.Parsers

object FormulaParser extends Parsers {
  // This is required for the token reader
  override type Elem = FormulaToken

  // Terminal variables
  private def constant = accept("Constant", { case CONSTANT(value) => Constant(value) })
  private def variable = accept("Variable", { case VARIABLE(id) => Variable(id) })
  private def terminal: Parser[FormulaAST] = constant | variable

  // Sum operators
//  private def expression: Parser[FormulaAST] =
//    terminal ~ (OPERATOR_ADD | OPERATOR_SUBTRACT) ~ terminal ^^ {
//      case left ~ OPERATOR_ADD ~ right => OperatorAdd(left, right)
//      case left ~ OPERATOR_SUBTRACT ~ right => OperatorSubtract(left, right)
//    }
  private def operator_sum: Parser[FormulaAST] =
    operator_product ~ opt((OPERATOR_ADD | OPERATOR_SUBTRACT) ~ operator_sum) ^^ {
      case left ~ None => left
      case left ~ Some(operator ~ right) => operator match {
        case OPERATOR_SUBTRACT => OperatorSubtract(left, right)
        case OPERATOR_ADD => OperatorAdd(left, right)
      }
    }

  // Product operators
  private def operator_product: Parser[FormulaAST] =
    terminal ~ opt((OPERATOR_MULTIPLY | OPERATOR_DIVIDE) ~ operator_product) ^^ {
      case left ~ None => left
      case left ~ Some(operator ~ right) => operator match {
        case OPERATOR_MULTIPLY => OperatorMultiply(left, right)
        case OPERATOR_DIVIDE => OperatorDivide(left, right)
      }
    }

  // Top-level rule
  private def formula: Parser[FormulaAST] = phrase(operator_sum)

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
