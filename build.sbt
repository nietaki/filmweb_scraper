name := "filmweb_scraper"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
  "com.github.tototoshi" %% "scala-csv" % "1.1.2",
  "org.jsoup" % "jsoup" % "1.8.1",
  "org.specs2" %% "specs2-core" % "2.4.15" % "test"
  )
