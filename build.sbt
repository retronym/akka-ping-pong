name := """minimal-akka-scala-seed"""

version := "1.0"
scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.14" withSources() withJavadoc(),
  "com.typesafe.akka" %% "akka-testkit" % "2.4.14" % "test" withSources() withJavadoc())


// javaOptions in run += "-agentpath:/Applications/YourKit-Java-Profiler-2016.02.app/Contents/Resources/bin/mac/libyjpagent.jnilib=tracing"
fork in run := true
