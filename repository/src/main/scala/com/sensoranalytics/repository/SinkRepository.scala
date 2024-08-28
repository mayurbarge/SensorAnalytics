package com.sensoranalytics.repository

import fs2.io.file.{Files, Path}
import cats.effect._
import com.sensoranalytics.SensorOutput
import fs2._
import fs2.data.csv._
import fs2.data.csv.generic.semiauto._
object SinkRepository {
  val outputFilePath = "output/result.txt"
  val targetPath = Path(outputFilePath)

  def writeCaseClassToCsv[A](
                              path: Path
                            )(implicit encoder: CsvRowEncoder[A, String]): Pipe[IO, A, Nothing] = {
    _.through(encodeUsingFirstHeaders(fullRows = true))
      .through(fs2.text.utf8.encode)
      .through(Files[IO].writeAll(path))
  }

  def writeToCSVFile(output: List[SensorOutput]) = {
    Stream
      .emits(output)
      .evalTap(IO.println)
      .through(writeCaseClassToCsv(targetPath))
      .compile
      .drain *> IO.println("Finished writing books to output.txt.")
  }
}

