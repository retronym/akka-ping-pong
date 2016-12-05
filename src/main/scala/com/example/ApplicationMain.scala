package com.example

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object ApplicationMain extends App {
  val executor = args.headOption.getOrElse("fork-join-executor")
  val config = ConfigFactory.parseString(
    s"""
      |akka {
      |  version = "2.4.14"
      |  actor {
      |    default-dispatcher {
      |      executor = "$executor"
      |      thread-pool-executor {
      |        core-pool-size-min = 1
      |        core-pool-size-max = 1
      |      }
      |      fork-join-executor {
      |        parallelism-min = 8
      |        parallelism-factor = 3.0
      |        parallelism-max = 64
      |        task-peeking-mode = "FIFO"
      |      }
      |    }
      |  }
      |}
    """.stripMargin)
  val system = ActorSystem("MyActorSystem", config.withFallback(ConfigFactory.defaultReference(getClass.getClassLoader)))
  val pingActor = system.actorOf(PingActor.props, "pingActor")
  pingActor ! PingActor.Initialize
  // This example app will ping pong 3 times and thereafter terminate the ActorSystem - 
  // see counter logic in PingActor
  system.awaitTermination()
}
