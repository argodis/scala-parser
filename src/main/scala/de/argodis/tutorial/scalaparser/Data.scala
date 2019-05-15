package de.argodis.tutorial.scalaparser
import java.io.{BufferedWriter, File, FileInputStream, FileWriter}

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

  def loadCsv[T](path: String)(implicit ct: ClassTag[T]): Either[String, List[T]] = {
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
  }.toEither match {
    case Left(error) => Left(error.getMessage)
    case Right(msg) => Right(msg)
  }

  def loadInput(path: String): Either[String, List[InputDataRow]] = loadCsv[InputDataRow](path)
  def loadFormula(path: String): Either[String, List[FormulaRow]] = loadCsv[FormulaRow](path)

  def writeOutput(path: String, data: List[String]): Either[String, String] = Try {
    val bw = new BufferedWriter(new FileWriter(new File(path)))
    bw.write(data.mkString("\n"))
    bw.close()
    s"Wrote ${data.size} lines"
  }.toEither match {
    case Left(error) => Left(error.getMessage )
    case Right(msg) => Right(msg)
  }
}
