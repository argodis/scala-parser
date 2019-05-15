package de.argodis.tutorial.scalaparser
import com.beust.jcommander.JCommander

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

  def main(args: Array[String]): Unit = {
    val arguments = parseCliArguments(args)
    val result = for {
      input <- Data.loadInput(arguments.input)
      formulas <- Data.loadFormula(arguments.formula)
    } yield formulas
  }
}
