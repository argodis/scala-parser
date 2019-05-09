import de.argodis.tutorial.scalaparser.parser.FormulaLexer
import de.argodis.tutorial.scalaparser.parser.tokens._
import org.scalatest.{FunSuite, Matchers}

class TestFormulaLexer extends FunSuite with Matchers {
  val SIMPLE_FORMULA = "($1 + $2) / ($3 - $4) + 100 * $5"
  val SIMPLE_FORMULA_TOKENS = List(
    BRACE_LEFT, VARIABLE(1), OPERATOR_ADD, VARIABLE(2), BRACE_RIGHT, OPERATOR_DIVIDE,
    BRACE_LEFT, VARIABLE(3), OPERATOR_SUBTRACT, VARIABLE(4), BRACE_RIGHT,
    OPERATOR_ADD, CONSTANT(100.0), OPERATOR_MULTIPLY, VARIABLE(5)
  )

  test("formula is tokenized correctly") {
    FormulaLexer
      .tokenize(SIMPLE_FORMULA)
      .map(tokens => tokens shouldBe SIMPLE_FORMULA_TOKENS)
  }

  test("lexer recognizes negative integers") {
    FormulaLexer
      .tokenize("-10")
      .map(tokens => tokens shouldBe List(CONSTANT(-10)))
  }

  test("lexer recognizes negative floating point numbers") {
    FormulaLexer
      .tokenize("-0.75")
      .map(tokens => tokens shouldBe List(CONSTANT(-0.75)))
  }

  test("lexer recognizes negative number after multiplication") {
    FormulaLexer
      .tokenize("1.32 * -0.35")
      .map(tokens => tokens shouldBe List(CONSTANT(1.32), OPERATOR_MULTIPLY, CONSTANT(-0.35)))
  }

  test("lexer recognizes negative number after addition") {
    FormulaLexer
      .tokenize("1.32 + -0.35")
      .map(tokens => tokens shouldBe List(CONSTANT(1.32), OPERATOR_ADD, CONSTANT(-0.35)))
  }

  test("lexer recognizes negative number after subtraction") {
    FormulaLexer
      .tokenize("1.32 - -0.35")
      .map(tokens => tokens shouldBe List(CONSTANT(1.32), OPERATOR_SUBTRACT, CONSTANT(-0.35)))
  }
}
