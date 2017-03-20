package edu.knoldus.bms1.actors

import akka.actor.Actor

class SeatActor extends Actor{

  var listOfSeats = List("A", "B", "C", "D", "E")
  override def receive = {
    case seat: String if listOfSeats.contains(seat)=>
      listOfSeats = listOfSeats diff List(seat)
      println(s"Current seats Available $listOfSeats")
      sender() ! "Seat booked Congratulations"

    case _ => sender() ! "Seat not available....sorry aap late ho gye"
  }

}
