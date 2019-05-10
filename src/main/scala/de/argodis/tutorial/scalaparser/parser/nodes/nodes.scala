package de.argodis.tutorial.scalaparser.parser.nodes

sealed trait FormulaAST
case class Constant(value: Double) extends FormulaAST

