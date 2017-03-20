package edu.knoldus.bms

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import edu.knoldus.bms.actors.{SeatActors, UserActor}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object BMSApp extends App{

  implicit val timeout = Timeout(1000 seconds)

  val system = ActorSystem("BMS")
  val seatProps = Props[SeatActors]
  val seatRef = system.actorOf(seatProps)
  val userProps = Props(classOf[UserActor],seatRef)

  val userRef1 = system.actorOf(userProps)
  val userRef2 = system.actorOf(userProps)
  val userRef3 = system.actorOf(userProps)
  val res1 = userRef1 ? 2
  val res2 = userRef2 ? 2
  val res3 = userRef3 ? 2
  res1.foreach(println)
  res2.foreach(println)
  res3.foreach(println)

}
