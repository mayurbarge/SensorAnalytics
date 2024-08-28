
SensorAnalytics
-------------------------------------------
This is a cats effect + FS2 based solution to aggregate sensor records from CSV File.

Problem Statement
---------------------------------
Python Case Study Assignment.docx file.

Requirements
-------------------------------
JDK 11 <br />
Scala 2.13.13 <br />
cats-effect <br />
fs2-data-csv <br />
fs2-data-csv-generic <br />
fs2-reactive-streams <br />
fs2-core <br />
fs2-io <br />

How to package ?
-------------------------
sbt assembly <br />
jar is created under target/scala2.13 folder.

How to Run ?
---------------------------
java -jar ./SensorAnalytics-assembly-0.1.0-SNAPSHOT.jar com.sensoranalytics.main.MainApp


Where is input ?
--------------------------------------
At the jar folder under input/assignment_dataset.csv

Where is output ?
---------------------------------------------
At the jar folder inside output/result.txt