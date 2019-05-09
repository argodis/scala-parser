package de.argodis.tutorial.scalaparser
import java.io.{File, FileInputStream}

import com.fasterxml.jackson.dataformat.csv.{CsvMapper, CsvSchema}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import de.argodis.tutorial.scalaparser.schema.{FormulaRow, InputDataRow}

import scala.io.Source
import scala.reflect.ClassTag
import scala.util.Try
import scala.collection.JavaConverters._

object Data {

  // This is a generic schema that reads the column names from the CSV header
  private val headerSchema: CsvSchema = CsvSchema
    .emptySchema
    .withHeader
    .withColumnSeparator(',')
    .withoutQuoteChar()

    def loadFileContent(filePath: String): Try[String] = {
      Try(Source
        .fromInputStream(new FileInputStream(new File(filePath)), "UTF-8")
        .getLines()
        .mkString("\n"))
    }

  def loadCsv[T](path: String)(implicit ct: ClassTag[T]): Try[List[T]] = {
    val mapper = new CsvMapper with ScalaObjectMapper
    mapper.registerModule(DefaultScalaModule)
    loadFileContent(path).map{content =>
      mapper
        .readerFor(ct.runtimeClass)
        .`with`(headerSchema)
        .readValues[T](content)
        .asInstanceOf[java.util.Iterator[T]]
        .asScala
        .toList
    }
  }

  def loadInput(path: String): Try[List[InputDataRow]] = loadCsv[InputDataRow](path)
  def loadFormula(path: String): Try[List[FormulaRow]] = loadCsv[FormulaRow](path)
}
