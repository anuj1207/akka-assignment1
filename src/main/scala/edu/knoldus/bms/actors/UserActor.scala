package edu.knoldus.bms.actors

import akka.actor.{Actor, ActorRef}
import akka.event.Logging

class UserActor(ref: ActorRef) extends Actor{

  val log = Logging(context.system, this)

  override def receive = {
    case msg: Int =>
      log.debug("Received the request ...now processing it")
      ref forward msg
    case _ => sender() ! "invalid input"
  }

}
