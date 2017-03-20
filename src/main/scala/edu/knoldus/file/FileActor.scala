package edu.knoldus.file

import java.nio.file.{Files, Paths}

import akka.actor.{Actor, ActorRef, Props}

import scala.io.Source
import akka.util.Timeout

import scala.concurrent.duration._

class FileActor(lineRef: ActorRef) extends Actor{

  var wordCount = 0
  var lines = 0
  var linesRead = 0
  implicit val timeout = Timeout(1000 seconds)
  var senderActor: Option[ActorRef] = None

  override def receive = {
    case filename: String if fileExists(filename)=>
      println(s"Path::$filename")
      senderActor = Some(sender)
      println(s"${fileExists(filename)}")
      for (line <- Source.fromFile(filename).getLines) {
        lines+=1
        lineRef ! line
      }
    case count: Int =>
      wordCount= wordCount+count
      linesRead+=1
      println(s"yooooo::$wordCount")
      if(linesRead>=lines) senderActor.foreach(_ ! wordCount)
    case _ => senderActor.foreach(_ ! "File not found!")
  }

  def fileExists(path: String): Boolean = {
    Files.exists(Paths.get(path))
  }

}

object FileActor {

  def props(ref: ActorRef) = {
    Props(classOf[FileActor], ref)
  }

}