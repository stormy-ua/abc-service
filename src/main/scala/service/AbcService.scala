package service

import journal.Logger
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
  import logging._

  implicit val log = Logger[this.type]
  implicit val containerInstance = Container(
    abcRepo = new AppAbcRepository(mongoInfo))

  val routes =
    respondWithMediaType(`application/javascript`) {
      get {
        path("documents") {
            exec {
              for {
                _ <- info("documents list requested")
                c <- abcRepository
                r <- c.all
              } yield r
            }
        }~
        path("documents" / IntNumber) { id => {
            exec {
              for {
                _ <- info(s"document with id=$id requested")
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
                _ <- info(s"create new document request: $d")
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
                _ <- info(s"request to delete the document with id=$id received")
                c <- abcRepository
                r <- c.deleteById(id)
              } yield r
            }
          }
        }
      }
    }
}
