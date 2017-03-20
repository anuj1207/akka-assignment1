import akka.actor.{Actor, Props}
import akka.util.Timeout
import akka.pattern.{ask, pipe}
import models.{Admin, User}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class PipingActor extends Actor{

  implicit val timeout = Timeout(1000 seconds)
  val greet = context.actorOf(Props[GreetingActor])
  override def receive = {
    case x: Admin =>
      val fMsg = greet ? (s"${x.name} full authority",x.id)
      fMsg pipeTo sender
    case x: User =>
      val fMsg = greet ? (s"${x.name} less authority",x.id)
      fMsg pipeTo sender
    case x =>
     Future{s"YOU $x!!! Not welcome(Neither models.User nor models.Admin)"} pipeTo sender
  }

}



