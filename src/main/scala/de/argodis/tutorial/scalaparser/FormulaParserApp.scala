package de.argodis.tutorial.scalaparser
import com.beust.jcommander.JCommander
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

  def parse(formulas: List[FormulaRow]): Either[String, List[(Long, FormulaAST)]] = Right(List())
//    // Attempt to parse all formulas
//    //    val trees = formulas.map(formulaRow => FormulaParser.parse2(formulaRow.formula))
//    // We need to preserve the formula ids, so that we can map them
//    val trees = formulas.map(formulaRow => FormulaParser.parse2(formulaRow.formula).map(ast => (formulaRow.id, ast)))
//
//    // Look for errors and return the first error found. Otherwise, return the list of formula ASTs, sorted by id:
//    trees collectFirst {
//      case Left(error) => error
//    } toLeft {
//      trees collect { case Right(r) => r }
//    }.sortBy(_._1)
//  }

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
      case Left(errorMessage) => println(s"Error: $errorMessage")
      case Right(msg) => println(s"Success: $msg")
    }
  }
}
