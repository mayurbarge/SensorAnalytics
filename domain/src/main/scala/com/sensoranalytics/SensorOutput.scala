package com.sensoranalytics

import cats.data.NonEmptyList
import fs2.data.csv._
import fs2.data.csv.generic.semiauto.deriveCsvRowEncoder
case class SensorOutput(
               dimension: String,
               averageValue: Double,
               maxValue: Double,
               minValue: Double,
               stdDev: Double
)
object SensorOutput {

  implicit val sensorOutputDecoder: CsvRowDecoder[SensorOutput, String] =
    new CsvRowDecoder[SensorOutput, String] {
      def apply(row: CsvRow[String]): DecoderResult[SensorOutput] =
        for {
          dimension <- row.as[String]("dimension")
          averageValue <- row.as[Double]("average_value")
          maxValue <- row.as[Double]("max_value")
          minValue <- row.as[Double]("min_value")
          stdDev <- row.as[Double]("std_dev")
        } yield {
          SensorOutput(
            dimension,
            averageValue,
            maxValue,
            minValue,
            stdDev
          )
        }
    }

  implicit val csvRowEncoder: CsvRowEncoder[SensorOutput, String] = deriveCsvRowEncoder

}

