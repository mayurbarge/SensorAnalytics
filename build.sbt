import sbt.Keys.libraryDependencies

import scala.collection.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.14"
ThisBuild / name := """SensorAnalytics"""
ThisBuild / assembly / logLevel := Level.Debug
ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

lazy val SensorAnalytics =
  project
    .in(file("."))
    .settings(
      Compile / mainClass := Some("com.sensoranalytics.main.MainApp"),
      assembly / mainClass := Some("com.sensoranalytics.main.MainApp")
    )
    .dependsOn(domain, repository, application)
    .aggregate(domain, repository, application)

lazy val domain =
  project
    .in(file("domain"))
    .settings(commonSettings)

lazy val repository =
  project
    .in(file("repository"))
    .settings(commonSettings)
    .settings(
      libraryDependencies ++= Seq (
        "org.typelevel" %% "cats-effect" % "3.5.4",
        "co.fs2" %% "fs2-core" % "3.10.2",
        "co.fs2" %% "fs2-io" % "3.10.2",
        "co.fs2" %% "fs2-reactive-streams" % "3.10.2",
        "org.gnieh" %% "fs2-data-csv" % "1.11.1",
        "org.gnieh" %% "fs2-data-csv-generic" % "1.11.1"
      )
    )
    .dependsOn(domain)

lazy val application =
  project
    .in(file("application"))
    .dependsOn(domain, repository)
    .settings(commonSettings)

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.typelevel" %% "cats-core" % "2.7.0",
    "org.typelevel" %% "cats-effect" % "3.3.5",
    "org.gnieh" %% "fs2-data-csv" % "1.11.1",
    "org.gnieh" %% "fs2-data-csv-generic" % "1.11.1",
    "dev.zio" %% "zio" % "2.1.8",
    "dev.zio" %% "zio-streams" % "2.1.8"
  ),
  Compile / console / scalacOptions --= Seq(
    "-Wunused:_",
    "-Xfatal-warnings"
  ),
  Compile / scalaSource := baseDirectory.value / "src/main/scala",
  Test / scalaSource := baseDirectory.value / "src/test/scala",
  Test / console / scalacOptions :=
    (Compile / console / scalacOptions).value
)
