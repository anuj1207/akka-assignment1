package edu.knoldus.file

import akka.actor.{ActorSystem, Props}
import akka.routing.FromConfig
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._
import akka.pattern.ask


object FileReadingApp extends App{

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /poolRouter {
      |   router = round-robin-pool
      |   resizer {
      |      pressure-threshold = 0
      |      lower-bound = 1
      |      upper-bound = 15
      |      messages-per-resize = 1
      |    }
      | }
      |}
    """.stripMargin
  )

  val system = ActorSystem("RouterSystem", config)
  val router = system.actorOf(FromConfig.props(Props[LineActor]), "poolRouter")
  val fileProps = Props(classOf[FileActor],router)//FileActor.props(router)
  val fileRef = system.actorOf(fileProps)

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  Thread.sleep(1000)

  val f = fileRef ? "./file.txt"
  f.map {
    res => println("Total no. of words: "+res)
  }

}
