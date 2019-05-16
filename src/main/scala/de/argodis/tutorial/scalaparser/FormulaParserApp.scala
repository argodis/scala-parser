package de.argodis.tutorial.scalaparser
import com.beust.jcommander.JCommander
import de.argodis.tutorial.scalaparser.parser.FormulaParser
import de.argodis.tutorial.scalaparser.parser.nodes.FormulaAST
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

  def parse(formulas: List[FormulaRow]): Either[String, List[(Long, FormulaAST)]] = {
    val trees = formulas.map(row => FormulaParser.parse(row.formula).map(ast => (row.id, ast)))
    trees
      .collectFirst { case Left(error) => error }
      .toLeft(trees.collect {case Right(r) => r}.sortBy(_._1))
  }

  def process(input: List[InputDataRow], trees: List[(Long, FormulaAST)]): Either[String, List[String]] = Right(List())

  def main(args: Array[String]): Unit = {
    val arguments = parseCliArguments(args)
    val result = for {
      input <- Data.loadInput(arguments.input)
      formulas <- Data.loadFormula(arguments.formula)
      trees <- parse(formulas)
      output <- process(input, trees)
      msg <- Data.writeOutput(arguments.output, output)
    } yield msg

    result match {
      case Left(errorMessage) => println(errorMessage)
      case Right(msg) => println(s"Success: $msg")
    }
  }
}
