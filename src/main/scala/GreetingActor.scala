import akka.actor.Actor

class GreetingActor extends Actor {

  override def receive = {
    case msg:(String,Int) =>
      sender() ! s"Welcome ${msg._1}! Have a warm and nice time, Your id is ${msg._2}"
  }

}
