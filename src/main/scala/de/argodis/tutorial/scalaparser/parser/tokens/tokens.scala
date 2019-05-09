package de.argodis.tutorial.scalaparser.parser.tokens

sealed trait FormulaToken
case object BRACE_LEFT extends FormulaToken
case object BRACE_RIGHT extends FormulaToken
case class VARIABLE(id: Int) extends FormulaToken
case class CONSTANT(value: Double) extends FormulaToken
case object OPERATOR_ADD extends FormulaToken
case object OPERATOR_SUBTRACT extends FormulaToken
case object OPERATOR_MULTIPLY extends FormulaToken
case object OPERATOR_DIVIDE extends FormulaToken

