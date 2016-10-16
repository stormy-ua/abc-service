import repositories.AbcRepository
import syntax._
import scalaz.Kleisli, scalaz.syntax.kleisli._, scalaz.Kleisli._
import scalaz.std.scalaFuture._
import scalaz.Kleisli
import scala.concurrent.{Future, ExecutionContext}
import ExecutionContext.Implicits.global

package object container {

  implicit def wrapK[A](value: Future[A]): RuntimeK[A] = value.liftKleisli

  val container: RuntimeK[Container] = Kleisli.ask

  val abcRepository: RuntimeK[AbcRepository] = container.andThen {
    Kleisli { c =>
      future.now {
        c.abcRepo
      }
    }
  }

  def exec[A](r: RuntimeK[A])(implicit c: Container): Future[A] = r(c)
}
