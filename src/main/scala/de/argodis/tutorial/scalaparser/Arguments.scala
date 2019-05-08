package de.argodis.tutorial.scalaparser
import com.beust.jcommander.Parameter

case class Arguments (
  @Parameter(names = Array("--input"), required = false, description = "Location of input data file")
  var input: String = "src/test/resources/data.csv",
  @Parameter(names = Array("--formula"), required = false, description = "Formula configuration file")
  var formula: String = "src/main/resources/formula.csv",
  @Parameter(names = Array("--output"), required = false, description = "Output data file")
  var output: String = "output.csv"
)
