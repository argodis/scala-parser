import de.argodis.tutorial.scalaparser.parser.{FormulaLexer, FormulaTokenReader}
import de.argodis.tutorial.scalaparser.parser.tokens._
import org.scalatest.{FunSuite, Matchers}

class TestFormulaTokenReader extends FunSuite with Matchers {
  val SIMPLE_FORMULA = "($1 + $2) / ($3 - $4) + 100 * $5"

  test("token reader can read the first token correctly") {
    FormulaLexer
      .tokenize(SIMPLE_FORMULA)
      .map(tokens => FormulaTokenReader(tokens))
      .map(reader => reader.first shouldBe BRACE_LEFT)
  }
}
