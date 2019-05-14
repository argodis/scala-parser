import de.argodis.tutorial.scalaparser.parser.{FormulaEvaluator, FormulaParser}
//import de.argodis.tutorial.scalaparser.parser.nodes._
import org.scalatest.{FunSuite, Matchers}

class TestFormulaEvaluator extends FunSuite with Matchers {
//  val SIMPLE_FORMULA = "($1 + $2) / ($3 - $4) + 100 * $5"

//  test("parser should produce the expression '(($1 + 2) * ($3 + 4)) / ($5 + 6)' ") {
//    val factor1 = OperatorAdd(Variable(1), Constant(2))
//    val factor2 = OperatorAdd(Variable(3), Constant(4))
//    val numerator = OperatorMultiply(factor1, factor2)
//    val denominator = OperatorAdd(Variable(5), Constant(6))
//    val tree = OperatorDivide(numerator, denominator)
//    FormulaParser.parse("(($1 + 2) * ($3 + 4)) / ($5 + 6)") shouldBe Right(tree)
//  }
  test("evaluation expression '1'") {
    FormulaParser
      .parse("1")
      .map(ast => FormulaEvaluator.evaluate(ast, Map()))
      .foreach(result => {
        println(s"--- RESULT ---")
        println(s"result: $result")
      })
  }
}
