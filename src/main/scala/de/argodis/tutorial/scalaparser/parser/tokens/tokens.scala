package de.argodis.tutorial.scalaparser.parser.tokens

trait FormulaToken
case object BRACE_LEFT extends FormulaToken
case object BRACE_RIGHT extends FormulaToken
case class VARIABLE(id: Int) extends FormulaToken
case class CONSTANT(value: Double) extends FormulaToken
// Arithmetic Operators
trait ArithmeticOperator extends FormulaToken
// Sum Operators
sealed trait SumOperator extends ArithmeticOperator
case object OPERATOR_ADD extends SumOperator
case object OPERATOR_SUBTRACT extends SumOperator
// Product Operators
sealed trait ProductOperator extends ArithmeticOperator
case object OPERATOR_MULTIPLY extends ProductOperator
case object OPERATOR_DIVIDE extends ProductOperator
// Modulo Operator
// case object OPERATOR_MODULO extends ArithmeticOperator

