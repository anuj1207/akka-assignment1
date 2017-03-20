package edu.knoldus.file

import akka.actor.Actor

class LineActor extends Actor{

  override def receive = {
    case msg: String =>
      println(s"Child::${msg.split(" ").length}")
      sender() forward msg.split(" ").length
    case _ =>
      sender() ! 0
  }

}
