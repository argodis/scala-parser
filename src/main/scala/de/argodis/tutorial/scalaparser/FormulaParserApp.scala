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
    println(s"input: ${arguments.input}")
    println(s"formula: ${arguments.formula}")
    println(s"output: ${arguments.output}")
    val input = Data.loadInput(arguments.input)
    val formula = Data.loadFormula(arguments.formula)
    input.foreach(list => println(list.size))
    formula.foreach(list => println(list.size))
  }
}
