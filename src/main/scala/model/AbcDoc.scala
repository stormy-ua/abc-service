package model

case class AbcDoc(id: Int, name: String) {
  require(name.length > 0, "name must not be empty")
  require(id > 0, "id must be greater than zero")
}