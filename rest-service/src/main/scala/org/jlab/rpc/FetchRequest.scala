package org.jlab.rpc

import akka.actor.{Actor, ActorSystem, Props}
import org.jlab.model.{Article, Start, Message}

/**
 * Created by scorpiovn on 1/14/15.
 */
object FetchRequest extends App {
  implicit val system = ActorSystem("LocalSystem")
  val requestActor = system.actorOf(Props[RequestActor], name = "RequestActor") // the local actor
  requestActor ! Start
}

class RequestActor extends Actor {
  // create the remote actor
  val remote = context.actorSelection("akka.tcp://simple-rpc@127.0.0.1:5150/user/RemoteFetchActor")
//  var counter = 0

  def receive = {
//    case Start =>
//      remote ! Message("Hello from the LocalActor")

    case Article(url, content) =>
      println(s"LocalActor received message: '$url' and '$content'")
      remote ! Article(url, content)
  }
}