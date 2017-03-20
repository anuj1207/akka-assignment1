package edu.knoldus.file

import akka.actor.{ActorSystem, Props}
import akka.routing.FromConfig
import akka.testkit.{ImplicitSender, TestKit}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import org.scalatest.{BeforeAndAfterAll, MustMatchers, WordSpecLike}
import

class FileReadingSpec extends TestKit(ActorSystem("test-system")) with WordSpecLike
  with BeforeAndAfterAll with MustMatchers with ImplicitSender {

  override protected def afterAll(): Unit = {
    system.terminate()
  }

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
  val fileProps = Props(classOf[FileActor],router)
  val fileRef = system.actorOf(fileProps)

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  Thread.sleep(1000)


}
