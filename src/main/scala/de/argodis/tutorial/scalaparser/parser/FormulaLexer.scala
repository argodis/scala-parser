package de.argodis.tutorial.scalaparser.parser

import scala.util.matching.Regex
import scala.util.parsing.combinator.RegexParsers
import de.argodis.tutorial.scalaparser.parser.tokens._

object FormulaLexer extends RegexParsers {
  override def skipWhitespace = true
  override val whiteSpace: Regex = "[ \t\r\f]+".r

  def tokenize(formula: String): Option[List[FormulaToken]] = Some(List())
}
