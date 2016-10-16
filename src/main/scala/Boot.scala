import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.util.Timeout
import service.AbcServiceActor
import spray.can.Http
import scala.concurrent.duration._
import akka.pattern.ask
import config._

object Boot extends App {

  implicit val system = ActorSystem("on-spray-can")

  val service = system.actorOf(Props[AbcServiceActor], "abc-service")

  implicit val timeout = Timeout(5.seconds)
  IO(Http) ? Http.Bind(service, interface = serviceInfo.host, port = serviceInfo.port)
}