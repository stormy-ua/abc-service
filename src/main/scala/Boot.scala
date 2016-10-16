import akka.actor.{Props, ActorSystem}
import akka.io.IO
import akka.util.Timeout
import service.AbcServiceActor
import spray.can.Http
import scala.concurrent.duration._
import akka.pattern.ask

object Boot extends App {

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("on-spray-can")

  // create and start our service actor
  val service = system.actorOf(Props[AbcServiceActor], "demo-service")

  implicit val timeout = Timeout(5.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = "localhost", port = 8080)
}