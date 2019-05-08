//------------------------------------------------------------------------------
// Project Information
//------------------------------------------------------------------------------
name := "scala-parser"
version := "0.1"
scalaVersion := "2.12.8"

//------------------------------------------------------------------------------
// Dependencies
//------------------------------------------------------------------------------
libraryDependencies ++= Seq (
  "com.beust"                     %  "jcommander"                       % Version.jcommander,
  "org.scala-lang.modules"        %%  "scala-parser-combinators"    % Version.parsercombinators,
  // Jackson
  "com.fasterxml.jackson.core"       % "jackson-databind"               % Version.jackson,
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-csv"         % Version.jackson,
  "com.fasterxml.jackson.module"     %% "jackson-module-scala"      % Version.jackson,
  "org.scalatest"                 %% "scalatest"                        % Version.scalatest         % "test"
)

//------------------------------------------------------------------------------
// Additional Options
//------------------------------------------------------------------------------
scalacOptions ++= Seq(
  "-deprecation",
  "-target:jvm-1.8",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Ywarn-unused-import"
)

javacOptions ++= Seq(
  "-source", "1.8",
  "-target", "1.8")

wartremover.wartremoverErrors in (Compile, compile) ++= Seq(
  wartremover.Wart.Any,
  wartremover.Wart.Serializable
)

//------------------------------------------------------------------------------
// Assembly
//------------------------------------------------------------------------------
// Disable tests during assembly - we should have tested before building the
// the final assembly
test in assembly := {}
