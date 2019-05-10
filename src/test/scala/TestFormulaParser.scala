import de.argodis.tutorial.scalaparser.parser.nodes.{Constant, Variable}
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

}
