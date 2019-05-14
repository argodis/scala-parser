package de.argodis.tutorial.scalaparser.parser

import de.argodis.tutorial.scalaparser.parser.nodes.FormulaAST

object FormulaEvaluator {
  def evaluate(formulaTree: FormulaAST, input: Map[Int, Option[Double]]): Option[Double] = {

    // Calculate numerical expressions
    def calculate(node: FormulaAST): Option[Double] = node match {
      case _ => None
    }

    // Run the calculation
    calculate(formulaTree)
  }
}
