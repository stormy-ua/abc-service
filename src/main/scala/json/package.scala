import model.{AbcOpResponse, AbcDoc}
import spray.json.DefaultJsonProtocol

package object json extends DefaultJsonProtocol {
  implicit val abcDocFormat = jsonFormat2(AbcDoc)
  implicit val abcOpResponseFormat = jsonFormat1(AbcOpResponse)

}