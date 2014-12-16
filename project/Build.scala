import sbt._
import sbt.Keys._

object Specs2scalacheckBuild extends Build {

  lazy val specs2scalacheck = Project(
    id = "specs2-scalacheck",
    base = file("."),
    settings = Seq(
      name := "specs2-scalacheck",
      organization := "com.daverstevens",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.11.4"
      // add other settings here
    )
  )
}
