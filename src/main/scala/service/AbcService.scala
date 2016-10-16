package service

import model.AbcDoc
import repositories.AbcRepository
import spray.http.MediaTypes._
import spray.routing._
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.httpx.marshalling.MetaMarshallers._
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

trait AbcService extends HttpService with AbcRepository {

  import json._

  val routes =
    respondWithMediaType(`application/javascript`) {
      get {
        path("documents") {
            complete {
              all
            }
        }~
        path("documents" / IntNumber) { id => {
            complete {
              getById(id)
            }
          }
        }
      }~
      post {
        path("documents") {
          entity(as[AbcDoc]) { d =>
            complete {
              insert(d)
            }
          }
        }
      }~
      delete {
        path("documents" / IntNumber) { id => {
          complete {
            deleteById(id)
          }
        }
        }
      }
    }
}
