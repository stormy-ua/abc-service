package service

import model.AbcDoc
import repositories.AppAbcRepository
import spray.http.MediaTypes._
import spray.routing._
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.httpx.marshalling.MetaMarshallers._
import scala.concurrent.{Promise, Future, ExecutionContext}
import ExecutionContext.Implicits.global
import scalaz.std.scalaFuture._

trait AbcService extends HttpService {

  import json._
  import container._
  import config._

  implicit val containerInstance = Container(
    abcRepo = new AppAbcRepository(mongoInfo))

  val routes =
    respondWithMediaType(`application/javascript`) {
      get {
        path("documents") {
            exec {
              for {
                c <- abcRepository
                r <- c.all
              } yield r
            }
        }~
        path("documents" / IntNumber) { id => {
            exec {
              for {
                c <- abcRepository
                r <- c.getById(id)
              } yield r
            }
          }
        }
      }~
      post {
        path("documents") {
          entity(as[AbcDoc]) { d =>
            exec {
              for {
                c <- abcRepository
                r <- c.insert(d)
              } yield r
            }
          }
        }
      }~
      delete {
        path("documents" / IntNumber) { id => {
            exec {
              for {
                c <- abcRepository
                r <- c.deleteById(id)
              } yield r
            }
          }
        }
      }
    }
}
