package repository

import domain.{Query, Team, Person}
import org.anormcypher.{NeoNode, Cypher, Neo4jREST}
import play.api.libs.json._
import play.api.libs.functional.syntax._

class FacewallRepo extends Repository {

    private case class DefaultPersonImplementation(id: String, name: String, picture: String) extends Person {
        def team = FacewallRepo.this.findTeamForPerson(this)
    }

    private case class DefaultTeamImplementation(id: String, name: String, colour: String) extends Team {
        def members = FacewallRepo.this.findPersonsForTeam(this)
    }

    implicit private val personReads: Reads[Person] = (
        (__ \ 'id).read[String] and
            (__ \ 'name).read[String] and
            (__ \ 'picture).read[String]
        )(DefaultPersonImplementation)

    implicit private val teamReads: Reads[Team] = (
        (__ \ 'id).read[String] and
            (__ \ 'name).read[String] and
            (__ \ 'colour).read[String]
        )(DefaultTeamImplementation)

    def findTeamForPerson(person: Person): Option[Team] = Cypher(
        """
          |START person = node(*)
          |MATCH person-[:TEAMMEMBER_OF]->team
          |WHERE person.id! = {personId}
          |RETURN team
        """.stripMargin).on("personId" -> person.id)().flatMap { row =>
        val nodeAsMap = row[NeoNode]("team").props
        val jsonValue = Json.toJson(nodeAsMap)(Neo4jREST.mapFormat)
        jsonValue.asOpt[Team]
    } match {
        case result if result.size > 1 => throw new IllegalStateException(s"Found more than one team for person, ${person.id}")
        case result => result.headOption
    }

    def listPersons: List[Person] = Cypher("START n = node(*) RETURN n")().flatMap { row =>
        val nodeAsMap = row[NeoNode]("n").props
        val jsonValue = Json.toJson(nodeAsMap)(Neo4jREST.mapFormat)
        jsonValue.asOpt[Person]
    }.toList

    def listTeams: List[Team] = Cypher(
        """
          |START team = node(*)
          |MATCH person-[:TEAMMEMBER_OF]->team
          |RETURN distinct team
        """.stripMargin
    )().flatMap { row =>
        val nodeAsMap = row[NeoNode]("team").props
        val jsonValue = Json.toJson(nodeAsMap)(Neo4jREST.mapFormat)
        jsonValue.asOpt[Team]
    }.toList

    def findPersonsForTeam(team: Team): List[Person] = Cypher(
        """
          |START person = node(*)
          |MATCH person-[:TEAMMEMBER_OF]->team
          |WHERE team.id! = {teamId}
          |RETURN person
        """.stripMargin).on("teamId" -> team.id)().flatMap { row =>
        val nodeAsMap = row[NeoNode]("person").props
        val jsonValue = Json.toJson(nodeAsMap)(Neo4jREST.mapFormat)
        jsonValue.asOpt[Person]
    }.toList

    def queryPersons(query: Query): List[Person] = Cypher(
        """
          |START n = node(*)
          |WHERE n.name! =~ {regex}
          |RETURN n;
        """.stripMargin).on("regex" -> query.toRegEx)().flatMap { row =>
        val nodeAsMap = row[NeoNode]("n").props
        val jsonValue = Json.toJson(nodeAsMap)(Neo4jREST.mapFormat)
        jsonValue.asOpt[Person]
    }.toList

    def queryTeams(query: Query): List[Team] = Cypher(
        """
          |START team = node(*)
          |MATCH person-[:TEAMMEMBER_OF]->team
          |WHERE team.name! =~ {regex}
          |RETURN distinct team
        """.stripMargin
    ).on("regex" -> query.toRegEx)().flatMap { row =>
        val nodeAsMap = row[NeoNode]("team").props
        val jsonValue = Json.toJson(nodeAsMap)(Neo4jREST.mapFormat)
        jsonValue.asOpt[Team]
    }.toList
}
