package repositories

import config.MongoInfo
import model.AbcDoc
import scala.concurrent.ExecutionContext.Implicits.global

class AppAbcRepository(mongoInfo: MongoInfo) extends AbcRepository {
  import org.mongodb.scala.MongoClient
  import org.mongodb.scala.bson.collection.immutable.Document
  import org.mongodb.scala.model.Filters._

  def toAbcDoc(d: Document): AbcDoc =
    AbcDoc(d("id").asInt32().getValue, d("name").asString().getValue)

  implicit def toDocument(d: AbcDoc): Document =
    Document("id" -> d.id, "name" -> d.name)

  private val collection = {
    val mongoClient = MongoClient(mongoInfo.uri)
    val database = mongoClient.getDatabase(mongoInfo.db)
    database.getCollection(mongoInfo.collection)
  }

  def all = {
    val query = for {
      d <- collection.find()
    } yield toAbcDoc(d)

    query.toFuture()
  }

  def getById(id: Int) = {
    val query = for {
      d <- collection.find(equal("id", id))
    } yield toAbcDoc(d)

    query.toFuture().map(s => s.headOption)
  }

  def insert(d: AbcDoc) =
    collection.insertOne(d).toFuture().map(c => c.toString())

  def deleteById(id: Int) =
    collection.deleteMany(equal("id", id)).toFuture().map(c => c.toString())
}