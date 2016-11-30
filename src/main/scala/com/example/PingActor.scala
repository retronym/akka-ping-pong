package com.example

import akka.actor.{Actor, ActorLogging, Props}

class PingActor extends Actor with ActorLogging {

  import PingActor._

  var counter = 0
  val pongActor = context.actorOf(PongActor.props, "pongActor")
  var start = 0L
  val total = 1000 * 10000
  def receive = {
    case Initialize =>
      start = System.nanoTime()
      log.info("In PingActor - starting ping-pong")
      pongActor ! PingMessage("ping")
    case PongActor.PongMessage(text) =>
      counter += 1
      if (counter == total) {
        log.info(s"${total * 2} messages, ${(System.nanoTime() - start) / 1000000000D} s, ${total * 2.0 / (System.nanoTime() - start) * 1000000000} msg/s")
        context.system.terminate()
      }
      else {
        if (counter % 100000 == 0)
          log.info(s"$counter/$total")
        sender() ! PingMessage("ping")
      }
  }
}

object PingActor {
  val props = Props[PingActor]

  case object Initialize

  case class PingMessage(text: String)

}