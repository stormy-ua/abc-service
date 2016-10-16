package service

import akka.actor.Actor

class AbcServiceActor extends Actor with AbcService {

  def actorRefFactory = context

  def receive = runRoute(routes)
}