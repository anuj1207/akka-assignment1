package edu.knoldus.bms1.actors

import akka.actor.{Actor, ActorRef, Props}

class UserActor(seatRef: ActorRef) extends Actor{

  override def receive = {
    case seat: String =>
      seatRef forward seat

    case _ => sender() ! "Invalid Input"
  }

}

object UserActor {

  def props(ref: ActorRef) = {
    Props(classOf[UserActor], ref)
  }

}