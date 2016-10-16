import container.Container

import scala.concurrent.Future
import scalaz.Kleisli

package object syntax extends FutureExt {
  type RuntimeK[A] = Kleisli[Future, Container, A]
}
