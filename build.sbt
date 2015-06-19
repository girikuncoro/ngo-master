import play.PlayJava

name := """ngo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "com.twilio.sdk" % "twilio-java-sdk" % "3.4.5",
  "com.google.guava"  % "guava" % "17.0",
  "com.h2database" % "h2" % "1.4.180",
  "org.jdbi" % "jdbi" % "2.55",
  "com.google.code.gson" % "gson" % "2.2.4",
  "joda-time" % "joda-time" % "2.3",
  "org.apache.httpcomponents" % "httpcore" % "4.3.2",
  "org.apache.httpcomponents" % "httpclient" % "4.3.4",
  "log4j" % "log4j" % "1.2.17",
  "junit" % "junit" % "4.11" % "test",
  "org.mockito" % "mockito-core" % "1.9.5"
//  "org.webjars" %% "webjars-play" % "2.2.0",
//  "org.webjars" % "bootstrap" % "2.3.1"
)


