package edu.knoldus.bms.actors

import akka.actor.Actor
import edu.knoldus.bms.models.Seat


class SeatActors extends Actor{

  var listOfSeats = List(Seat("A"), Seat("B"), Seat("C"), Seat("D"), Seat("E"))

  override def receive: PartialFunction[Any, Unit] = {
    case msg: Int if listOfSeats.size >= msg =>
      val size = listOfSeats.size
      val bookedSeats = listOfSeats.drop(size - msg)
      listOfSeats = listOfSeats.take(size - msg)
      sender() ! bookedSeats

    case _ =>
      println("Sorrrrrryyyyy")
      sender() ! "Seats not available"
  }

}
