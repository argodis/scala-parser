package de.argodis.tutorial.scalaparser
import com.beust.jcommander.JCommander
import de.argodis.tutorial.scalaparser.schema.{FormulaRow, InputDataRow}

object FormulaParserApp {

  def parseCliArguments(args: Array[String]): Arguments = {
    val arguments = Arguments()
    JCommander
      .newBuilder()
      .addObject(arguments)
      .build()
      .parse(args.toArray: _*)
    // Return the parsed arguments
    arguments
  }

  def process(input: List[InputDataRow], trees: List[FormulaRow]): Either[String, List[String]] = Right(List())

  def main(args: Array[String]): Unit = {
    val arguments = parseCliArguments(args)
    val result = for {
      input <- Data.loadInput(arguments.input)
      formulas <- Data.loadFormula(arguments.formula)
    } yield process(input, formulas)

    result match {
      case Left(errorMessage) => println(s"Error: $errorMessage")
      case Right(msg) => println(s"Success: $msg")
    }
  }
}
