package domain

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Person(name: String, picture: String)

object Person {
    implicit val personReads = Json.reads[Person]
}