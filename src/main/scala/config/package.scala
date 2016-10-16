import com.typesafe.config.ConfigFactory

package object config {

  private lazy val config = {
    ConfigFactory.load().getConfig("abc-service")
  }

  val serviceInfo =
    ServiceInfo(config.getString("service.host"), config.getInt("service.port"))

  val mongoInfo =
    MongoInfo(config.getString("mongo.uri"), config.getString("mongo.db"), config.getString("mongo.collection"))
}
