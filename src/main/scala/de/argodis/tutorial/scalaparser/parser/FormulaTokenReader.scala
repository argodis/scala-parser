package de.argodis.tutorial.scalaparser.parser

import de.argodis.tutorial.scalaparser.parser.tokens.FormulaToken
import scala.util.parsing.input.{NoPosition, Position, Reader}

case class FormulaTokenReader(tokens: List[FormulaToken]) extends Reader[FormulaToken] {
  def first: FormulaToken = tokens.head
  def atEnd: Boolean = tokens.isEmpty
  def pos: Position = NoPosition
  def rest: Reader[FormulaToken] = FormulaTokenReader(tokens.tail)
}

