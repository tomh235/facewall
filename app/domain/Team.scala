package domain

import play.api.libs.json.Json

case class Team(id: String, name: String)

object Team {
    implicit val teamReads = Json.reads[Team]
}
