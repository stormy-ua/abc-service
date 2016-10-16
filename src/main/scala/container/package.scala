import repositories.AbcRepository
import syntax._
import scalaz.Kleisli, scalaz.syntax.kleisli._, scalaz.Kleisli._
import scalaz.std.scalaFuture._
import scalaz.Kleisli
import scala.concurrent.{Future, ExecutionContext}
import ExecutionContext.Implicits.global
import spray.http.MediaTypes._
import spray.routing._
import spray.httpx.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import spray.httpx.marshalling.MetaMarshallers._
import spray.httpx.marshalling.{ToResponseMarshaller, ToResponseMarshallable}

package object container extends spray.routing.Directives {

  implicit def wrapK[A](value: Future[A]): RuntimeK[A] = value.liftKleisli

  val container: RuntimeK[Container] = Kleisli.ask

  val abcRepository: RuntimeK[AbcRepository] = container.andThen {
    Kleisli { c =>
      future.now {
        c.abcRepo
      }
    }
  }

  def exec[A](r: RuntimeK[A])(implicit c: Container, m: ToResponseMarshaller[Future[A]]): StandardRoute = {
    complete {
      ToResponseMarshallable.isMarshallable(r(c))(m)
    }
  }
}
