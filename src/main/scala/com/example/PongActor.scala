package com.example

import akka.actor.{Actor, Props}

class PongActor extends Actor {
  import PongActor._

  def receive = {
  	case PingActor.PingMessage(text) => 
  	  sender() ! PongMessage("pong")
  }	
}

object PongActor {
  val props = Props[PongActor]
  case class PongMessage(text: String)
}
