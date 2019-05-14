package de.argodis.tutorial.scalaparser.parser

import de.argodis.tutorial.scalaparser.parser.nodes.{Constant, FormulaAST, OperatorAdd, OperatorDivide, OperatorMultiply, OperatorSubtract, Variable}

object FormulaEvaluator {

  // Handles problematic floating point values
  def sanitize(x: Double): Option[Double] = x match {
    case value if java.lang.Double.isNaN(value) => None
    case Double.PositiveInfinity => None
    case Double.NegativeInfinity => None
    case value => Some(value)
  }

  // Take options as input, return an option as output
  def safeOp(left: Option[Double], right: Option[Double], op: (Double, Double) => Double): Option[Double] = {
    val result = for {
      x <- left
      y <- right
    } yield op(x, y)
    // We don't want to deal with NaN, Infinity, etc
    result.flatMap(sanitize)
  }

  def evaluate(formulaTree: FormulaAST, input: Map[Int, Double]): Option[Double] = {

    // Calculate numerical expressions
    def calculate(node: FormulaAST): Option[Double] = node match {
      case Constant(value) => Some(value)
      case Variable(id) => input.get(id)
      case OperatorAdd(left, right) => safeOp(calculate(left), calculate(right), _ + _)
      case OperatorSubtract(left, right) => safeOp(calculate(left), calculate(right), _ - _)
      case OperatorMultiply(left, right) => safeOp(calculate(left), calculate(right), _ * _)
      case OperatorDivide(left, right) => safeOp(calculate(left), calculate(right), _ / _)
      case _ => None
    }

    // Run the calculation
    calculate(formulaTree)
  }
}
