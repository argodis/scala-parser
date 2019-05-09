import de.argodis.tutorial.scalaparser.Data
import de.argodis.tutorial.scalaparser.schema.{FormulaRow, InputDataRow}
import org.scalatest.{FunSuite, Matchers}

import scala.util.Failure

class TestData extends FunSuite with Matchers {

  private val INPUT_DATA_FILE: String = "src/test/resources/data.csv"
  private val FORMULA_FILE: String = "src/main/resources/formula.csv"

  // We are not going to focus too much on this
  test("an attempt to load a file from a wrong path should result in a failure") {
    Data.loadFileContent("./non-existing-file") shouldBe a [Failure[_]]
  }

  test("loading a non-empty file successfully should result in a non-empty string") {
    Data.loadFileContent(INPUT_DATA_FILE).map(content => content should not be "")
  }

  //
  test("attempting to load a non-existing file should result in an empty sequence") {
    Data.loadCsv[String]("./non-existing-file") shouldBe a [Failure[_]]
  }

  test("loading a non-empty file successfully should result in a non-empty list of rows") {
    Data.loadCsv[String](INPUT_DATA_FILE).map(list => list.size should not be 0)
  }

  test("can load the input data from the resource folder") {
    Data.loadCsv[InputDataRow](INPUT_DATA_FILE).map(list => list.size shouldBe 1000)
  }

  test("can load the formula configuration file from the resource folder") {
    Data.loadCsv[FormulaRow](FORMULA_FILE).map(list => list.size shouldBe 3)
  }

}
