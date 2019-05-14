package de.argodis.tutorial.scalaparser.parser.nodes

sealed trait FormulaAST
case class Constant(value: Double) extends FormulaAST
case class Variable(value: Int) extends FormulaAST
case class OperatorAdd(left: FormulaAST, right: FormulaAST) extends FormulaAST
case class OperatorSubtract(left: FormulaAST, right: FormulaAST) extends FormulaAST
case class OperatorMultiply(left: FormulaAST, right: FormulaAST) extends FormulaAST
case class OperatorDivide(left: FormulaAST, right: FormulaAST) extends FormulaAST
