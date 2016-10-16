package repositories

import model.AbcDoc
import scala.concurrent.Future

trait AbcRepository {

  def all: Future[Seq[AbcDoc]]

  def getById(id: Int): Future[Option[AbcDoc]]

  def insert(d: AbcDoc): Future[String]

  def deleteById(id: Int): Future[String]
}