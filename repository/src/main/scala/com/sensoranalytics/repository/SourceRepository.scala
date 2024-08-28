package com.sensoranalytics.repository

import fs2.io.file.{Files, Flags, Path}
import fs2.text

import cats.effect._
import com.sensoranalytics.SensorReading
import fs2._
import fs2.data.csv._
import fs2.data.csv.generic.semiauto._

object SourceRepository {
  val inputFilePath = "input/assignment_dataset.csv"

  val input = Files[IO]
    .readAll(Path(inputFilePath), 1024, Flags.Read)
    .through(text.utf8.decode)
    .through(decodeUsingHeaders[SensorReading]())

}