import de.argodis.tutorial.scalaparser.parser.nodes.{Constant, OperatorAdd, OperatorDivide, OperatorMultiply, OperatorSubtract, Variable}
import de.argodis.tutorial.scalaparser.parser.FormulaParser
import org.scalatest.{FunSuite, Matchers}

class TestFormulaParser extends FunSuite with Matchers {
  val SIMPLE_FORMULA = "($1 + $2) / ($3 - $4) + 100 * $5"

  test("parser can produce a constant") {
    FormulaParser
      .parse("-23.5")
      .map(formulaAST => formulaAST shouldBe Constant(-23.5))
  }

  test("parser should fail to parse expression '5 10'") {
    FormulaParser.parse("5 10") shouldBe a ('Left)
  }

  test("parser can produce a variable") {
    FormulaParser
      .parse("$7")
      .map(formulaAST => formulaAST shouldBe Variable(7))
  }

  test("parser should fail to parse expression '$2 $9'") {
    FormulaParser.parse("$2 $9") shouldBe a ('Left)
  }

  test("parser should fail to parse expression '4.35 $12'") {
    FormulaParser.parse("4.35 $12") shouldBe a ('Left)
  }

  test("parser can produce the expression 3.2 + 7.8") {
    FormulaParser.parse("3.2 + 7.8") shouldBe Right(OperatorAdd(Constant(3.2), Constant(7.8)))
  }

  test("parser can produce the expression -0.75 + $3") {
    FormulaParser.parse("-0.75 + $3") shouldBe Right(OperatorAdd(Constant(-0.75), Variable(3)))
  }

  test("parser can produce the expression $8 + $9") {
    FormulaParser.parse("$8 + $9") shouldBe Right(OperatorAdd(Variable(8), Variable(9)))
  }

  test("parser should fail to parse the expression '$1 + '") {
    FormulaParser.parse("$1 + ") shouldBe a ('Left)
  }

  test("parser should fail to parse the expression '+ $3'") {
    FormulaParser.parse("+ $3") shouldBe a ('Left)
  }

  test("parser can produce the expression 3.2 - 7.8") {
    FormulaParser.parse("3.2 - 7.8") shouldBe Right(OperatorSubtract(Constant(3.2), Constant(7.8)))
  }

  test("parser can produce the expression -0.75 - $3") {
    FormulaParser.parse("-0.75 - $3") shouldBe Right(OperatorSubtract(Constant(-0.75), Variable(3)))
  }

  test("parser can produce the expression $8 - $9") {
    FormulaParser.parse("$8 - $9") shouldBe Right(OperatorSubtract(Variable(8), Variable(9)))
  }

  test("parser should fail to parse the expression '$1 - '") {
    FormulaParser.parse("$1 - ") shouldBe a ('Left)
  }

  test("parser should fail to parse the expression '- $3'") {
    FormulaParser.parse("- $3") shouldBe a ('Left)
  }

  test("parser produce the expression '$1 + $2 + $3'") {
    val tree = OperatorAdd(Variable(1), OperatorAdd(Variable(2), Variable(3)))
    FormulaParser.parse("$1 + $2 + $3") shouldBe Right(tree)
  }

  test("parser produce the expression '$1 - $2 - $3'") {
    val tree = OperatorSubtract(Variable(1), OperatorSubtract(Variable(2), Variable(3)))
    FormulaParser.parse("$1 - $2 - $3") shouldBe Right(tree)
  }

  test("parser produce the expression '$1 + $2 - $3'") {
    val tree = OperatorAdd(Variable(1), OperatorSubtract(Variable(2), Variable(3)))
    FormulaParser.parse("$1 + $2 - $3") shouldBe Right(tree)
  }

  test("parser should fail to parse the expression '$1 + $2 $3'") {
    FormulaParser.parse("$1 + $2 $3") shouldBe a ('Left)
  }

  test("parser should fail to parse the expression '$1 $2 - $3'") {
    FormulaParser.parse("$1 $2 - $3") shouldBe a ('Left)
  }

  test("parser should produce the expression '$1 * $2' ") {
    val tree = OperatorMultiply(Variable(1), Variable(2))
    FormulaParser.parse("$1 * $2") shouldBe Right(tree)
  }

  test("parser should produce the expression '$1 / $2' ") {
    val tree = OperatorDivide(Variable(1), Variable(2))
    FormulaParser.parse("$1 / $2") shouldBe Right(tree)
  }

  test("parser should produce the expression '$1 * $2 + $3' ") {
    val tree = OperatorAdd(OperatorMultiply(Variable(1), Variable(2)), Variable(3))
    FormulaParser.parse("$1 * $2 + $3") shouldBe Right(tree)
  }

  test("parser should produce the expression '$1 - $2 / $3' ") {
    val tree = OperatorSubtract(Variable(1), OperatorDivide(Variable(2), Variable(3)))
    FormulaParser.parse("$1 - $2 / $3") shouldBe Right(tree)
  }

  test("parser should produce the expression '$1 * $2 + $3 / $4 - $5' ") {
    val mul = OperatorMultiply(Variable(1), Variable(2))
    val div = OperatorDivide(Variable(3), Variable(4))
    val tree = OperatorAdd(mul, OperatorSubtract(div, Variable(5)))
    FormulaParser.parse("$1 * $2 + $3 / $4 - $5") shouldBe Right(tree)
  }

  test("parser should produce the expression '(1)'") {
    FormulaParser.parse("(1)") shouldBe Right(Constant(1))
  }

  test("parser should produce the expression '((1))'") {
    FormulaParser.parse("((1))") shouldBe Right(Constant(1))
  }

  test("parser should produce the expression '($1)'") {
    FormulaParser.parse("($1)") shouldBe Right(Variable(1))
  }

  test("parser should produce the expression '($1 + $2)'") {
    val tree = OperatorAdd(Variable(1), Variable(2))
    FormulaParser.parse("($1 + $2)") shouldBe Right(tree)
  }

  test("parser should produce the expression '($1 * $2)'") {
    val tree = OperatorMultiply(Variable(1), Variable(2))
    FormulaParser.parse("($1 * $2)") shouldBe Right(tree)

  }

  test("parser should produce the expression '($1 + $2) + ($3 + $4)'") {
    val factor1 = OperatorAdd(Variable(1), Variable(2))
    val factor2 = OperatorAdd(Variable(3), Variable(4))
    val tree = OperatorAdd(factor1, factor2)
    FormulaParser.parse("($1 + $2) + ($3 + $4)") shouldBe Right(tree)
  }

  test("parser should produce the expression '($1 + 10) * ($2 - 4.25)' ") {
    val factor1 = OperatorAdd(Variable(1), Constant(10))
    val factor2 = OperatorSubtract(Variable(2), Constant(4.25))
    val tree = OperatorMultiply(factor1, factor2)
    FormulaParser.parse("($1 + 10) * ($2 - 4.25)") shouldBe Right(tree)
  }

  test("parser should produce the expression '(($1 + 2) * ($3 + 4)) / ($5 + 6)' ") {
    val factor1 = OperatorAdd(Variable(1), Constant(2))
    val factor2 = OperatorAdd(Variable(3), Constant(4))
    val numerator = OperatorMultiply(factor1, factor2)
    val denominator = OperatorAdd(Variable(5), Constant(6))
    val tree = OperatorDivide(numerator, denominator)
    FormulaParser.parse("(($1 + 2) * ($3 + 4)) / ($5 + 6)") shouldBe Right(tree)
  }
}
