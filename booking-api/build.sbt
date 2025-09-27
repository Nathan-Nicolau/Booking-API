name := """booking-api"""
organization := "nathan.nicolau"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.16"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.play" %% "play-slick" % "5.2.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.2.0",
  "org.postgresql" % "postgresql" % "42.7.3"// driver do banco
)


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "nathan.nicolau.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "nathan.nicolau.binders._"
