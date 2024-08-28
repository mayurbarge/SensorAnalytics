package com.sensoranalytics

import cats.data.NonEmptyList
import fs2.data.csv._

import java.text.SimpleDateFormat
import java.util.Date

case class SensorReading(
                          timestamp: Option[Date],
                          sensorId: Int,
                          value: Option[Double],
                          modelName: String = "",
                          location: String = "",
                          temperature: Option[Double],
                          humidity: Option[Double],
                          airQuality: Option[Int],
                          altitude: Option[Int],
                          weatherCondition: String = "")
object SensorReading {
  implicit object ValueOrdering extends Ordering[Double] {
    override def compare(x: Double, y: Double): Int = x compare y
  }

  implicit val sensorReadingDecoder: CsvRowDecoder[SensorReading, String] =
     new CsvRowDecoder[SensorReading, String] {
       def apply(row: CsvRow[String]): DecoderResult[SensorReading] =
         for {
           timestamp <- row.as[String]("timestamp")
           sensorId <- row.as[Int]("sensor_id")
           value <- row.asOptional[Double]("value")
           modelName <- row.as[String]("model_name")
           location <- row.as[String]("location")
           temperature <- row.asOptional[Double]("temperature")
           humidity <- row.asOptional[Double]("humidity")
           airQuality <- row.asOptional[Int]("air_quality")
           altitude <- row.asOptional[Int]("altitude")
           weatherCondition <- row.as[String]("weather_condition")
         } yield {
           val date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(timestamp)
           SensorReading(
             Option.apply(date),
             sensorId,
             value,
             modelName,
             location,
             temperature,
             humidity,
             airQuality,
             altitude,
             weatherCondition
           )
         }
     }

   implicit val sensorReadingEncoder: CsvRowEncoder[SensorReading, String] =
     new CsvRowEncoder[SensorReading, String] {
       def apply(reading: SensorReading): CsvRow[String] =
         CsvRow.fromNelHeaders(
           NonEmptyList.of(
             (reading.timestamp.toString, "timestamp"),
             (reading.sensorId.toString, "sensor_id"),
             (reading.value.map(_.toString).getOrElse(""), "value"),
             (reading.modelName, "model_name"),
             (reading.location, "location"),
             (reading.temperature.map(_.toString).getOrElse(""), "temperature"),
             (reading.humidity.map(_.toString).getOrElse(""), "humidity"),
             (reading.airQuality.map(_.toString).getOrElse(""), "air_quality"),
             (reading.altitude.map(_.toString).getOrElse(""), "altitude"),
             (reading.weatherCondition, "weather_condition")
           )
         )
     }
}