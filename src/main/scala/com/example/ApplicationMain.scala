package com.example

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object ApplicationMain extends App {
  val config = ConfigFactory.parseString(
    """
      |akka {
      |  actor {
      |    default-dispatcher {
      |      executor = "thread-pool-executor"
      |      thread-pool-executor {
      |        core-pool-size-min = 1
      |        core-pool-size-max = 1
      |      }
      |    }
      |  }
      |}
    """.stripMargin)
  val system = ActorSystem("MyActorSystem", config)
  val pingActor = system.actorOf(PingActor.props, "pingActor")
  pingActor ! PingActor.Initialize
  // This example app will ping pong 3 times and thereafter terminate the ActorSystem - 
  // see counter logic in PingActor
  system.awaitTermination()
}