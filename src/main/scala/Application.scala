import akka.actor.{ActorSystem, Props}
import akka.util.Timeout
import models.{Admin, User}
import scala.concurrent.duration._
import akka.pattern.ask
import scala.concurrent.ExecutionContext.Implicits.global

object Application extends App{

  val system = ActorSystem("Welcome")
  val props = Props[PipingActor]
  val ref = system.actorOf(props)

  implicit val timeout = Timeout(1000 seconds)

  val welcomeMsg = ref ? Admin(1, "Anuj")
  welcomeMsg.foreach(println)
  val welcomeMsg2 = ref ? User(2, "Gitika")
  welcomeMsg2.foreach(println)
  val welcomeMsg3 = ref ? "Anuj"
  welcomeMsg3.foreach(println)

}
