import de.argodis.tutorial.scalaparser.parser.{FormulaEvaluator, FormulaParser}
import org.scalatest.{FunSuite, Matchers}

class TestFormulaEvaluator extends FunSuite with Matchers {
  test("evaluate expression '1'") {
    FormulaParser
      .parse("1")
      .map(ast => FormulaEvaluator.evaluate(ast, Map()))
      .map(result => result shouldBe Some(1.0))
  }

  test("evaluate expression '$1' where '$1 = -4.25' ") {
    FormulaParser
      .parse("$1")
      .map(ast => FormulaEvaluator.evaluate(ast, Map(1 -> -4.25)))
      .map(result => result shouldBe Some(-4.25))
  }

  test("convert a proper double to Some(value)") {
    FormulaEvaluator.sanitize(0.25)  shouldBe Some(0.25)
  }

  test("convert a scala.Double.NaN to None") {
    FormulaEvaluator.sanitize(scala.Double.NaN)  shouldBe None
  }

  test("convert a positive infinity to None") {
    val result = Some(1.25).map(value => value / 0.0)
    FormulaEvaluator.sanitize(result.get) shouldBe None
  }

  test("convert a negative infinity to None") {
    val result = Some(1.25).map(value => - value / 0.0)
    FormulaEvaluator.sanitize(result.get) shouldBe None
  }

  test("safeOp returns a result when both arguments are defined") {
    FormulaEvaluator.safeOp(Some(1), Some(1), _ + _) shouldBe Some(2)
  }

  test("safeOp returns None when the left argument is None") {
    FormulaEvaluator.safeOp(None, Some(1), _ + _) shouldBe None
  }

  test("safeOp returns None when the right argument is None") {
    FormulaEvaluator.safeOp(Some(1), None, _ + _) shouldBe None
  }

  test("safeOp returns None when both arguments are None") {
    FormulaEvaluator.safeOp(None, None, _ + _) shouldBe None
  }

  test("safeOp returns None when a division by zero occurs") {
    FormulaEvaluator.safeOp(Some(0.25), Some(0.0), _ / _) shouldBe None
  }

  test("evaluate expression '$1 - 1'") {
    FormulaParser
      .parse("$1 - 1")
      .map(ast => FormulaEvaluator.evaluate(ast, Map(1 -> 1)))
      .map(result => result shouldBe Some(0.0))
  }

  test("evaluate expression '$1 - $1'") {
    FormulaParser
      .parse("$1 - $1")
      .map(ast => FormulaEvaluator.evaluate(ast, Map(1 -> 1)))
      .map(result => result shouldBe Some(0.0))
  }

  test("evaluate expression '$1 - $2'") {
    FormulaParser
      .parse("$1 - $2")
      .map(ast => FormulaEvaluator.evaluate(ast, Map(1 -> 1, 2 -> 1)))
      .map(result => result shouldBe Some(0.0))
  }

  test("evaluate expression with a missing second argument '$1 - $2'") {
    FormulaParser
      .parse("$1 - $2")
      .map(ast => FormulaEvaluator.evaluate(ast, Map(1 -> 1)))
      .map(result => result shouldBe None)
  }

  test("evaluate expression '($1 + $2) * ($3 + $4)'") {
    FormulaParser
      .parse("($1 + $2) * ($3 + $4)")
      .map(ast => FormulaEvaluator.evaluate(ast, Map(1 -> 1, 2 -> 1, 3 -> 3, 4 -> 2)))
      .map(result => result shouldBe Some(10.0))
  }

}
