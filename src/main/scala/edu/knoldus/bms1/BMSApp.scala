package edu.knoldus.bms1

import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask
import edu.knoldus.bms1.actors.{SeatActor, UserActor}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


object BMSApp extends App{

  val system = ActorSystem("BMS")
  val seatProps = Props[SeatActor]
  val seatRef = system.actorOf(seatProps)
  val userProps = UserActor.props(seatRef)//Props(classOf[UserActor],seatRef)

  val userRef = system.actorOf(userProps)
  val userRef1 = system.actorOf(userProps)
  val userRef2 = system.actorOf(userProps)

  implicit val timeout = Timeout(1000 seconds)

  val welcomeMsg = userRef ? "A"
  welcomeMsg.foreach(println)
  val welcomeMsg2 = userRef1 ? "A"
  welcomeMsg2.foreach(println)
  val welcomeMsg3 = userRef2 ? "A"
  welcomeMsg3.foreach(println)
}
