package com.sensoranalytics.main

import cats.Monoid
import cats.effect._
import com.sensoranalytics.SensorReading
import com.sensoranalytics.repository.{SinkRepository, SourceRepository}
import com.sensoranalytics.mapper.SensorReadingMapper

import java.util.Calendar

object MainApp extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    val filter = (reading: SensorReading) => reading.timestamp.map(_.getHours.toString).getOrElse("")
    val sensorOutput = for {
      sensorReadings <- SourceRepository.input.compile.toList.map(_.distinct)
    } yield {SensorReadingMapper.mapReadings(sensorReadings, filter) }

    val program: IO[Unit] = sensorOutput.flatMap(SinkRepository.writeToCSVFile)

    /*val fileOutput = for {
      output <- sensorOutput
    } yield { SinkRepository.writeToCSVFile(output) }
*/
    //fileOutput.flatten.as(ExitCode.Success)

    program.as(ExitCode.Success)
  }
}