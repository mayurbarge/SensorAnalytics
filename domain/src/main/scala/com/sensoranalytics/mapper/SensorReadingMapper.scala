package com.sensoranalytics.mapper

import com.sensoranalytics.{SensorOutput, SensorReading}

object SensorReadingMapper {
  def mapReadings(readings: List[SensorReading], f: SensorReading => String) = {
    readings.groupBy(f).map {
      import SensorReading._
      readingsByTimeStamp => {
        val timeStamp = readingsByTimeStamp._1
        val values: Seq[Double] = readingsByTimeStamp._2.map(_.value.getOrElse(0))
        val sorted = values.sorted
        val average = sorted.sum / sorted.size
        val stdDev = Math.sqrt(sorted.map(v => Math.pow(v - average, 2.0)).sum / (sorted.size - 1))

        SensorOutput(timeStamp, average, sorted.max, sorted.min, stdDev)
      }
    }.toList
  }

}
