import journal.Logger
import syntax._
import scalaz.concurrent.Future
import scalaz.Kleisli, scalaz.syntax.kleisli._, scalaz.Kleisli._
import scalaz.std.scalaFuture._
import scalaz.Kleisli

package object logging {
  def info(message: String)(implicit L: Logger): RuntimeK[Unit] = Kleisli {c =>
    future.now {
      L.info(message)
    }
  }
}
