package syntax

import scala.concurrent.{Promise, Future}

trait FutureExt {
  object future {
    def now[A](v: => A): Future[A] = Promise[A].success(v).future
  }
}
