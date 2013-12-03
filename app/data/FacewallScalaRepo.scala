package data

import domain.{Query, Team, Person}
import org.anormcypher.{NeoNode, Cypher, Neo4jREST}
import play.api.libs.json._
import play.api.libs.functional.syntax._

import scala.collection.JavaConverters._

class FacewallScalaRepo extends ScalaRepository {

    private case class DefaultPersonImplementation(id: String, name: String, picture: String) extends Person {
        def team = FacewallScalaRepo.this.findTeamForPerson(this)
    }

    private case class DefaultTeamImplementation(id: String, name: String, colour: String) extends Team {
        def members = FacewallScalaRepo.this.findPersonsForTeam(this).asJava
    }

    private val noTeam: Team = new Team {
        def members(): java.util.List[Person] = Nil.asJava.asInstanceOf[java.util.List[Person]]
        def name(): String = ""
        def id(): String = {
            throw new UnsupportedOperationException("cannot get the id of no team")
        }
        def colour(): String = "grey"
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

  def addPerson(person: Person) {}

  def findTeamForPerson(person: Person): Team = Cypher(
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
        case result if result.size > 1 => throw new IllegalStateException("Found more than one team for person, ${person.id}")
        case result if result.isEmpty => noTeam
        case result => result.headOption.get
    }

    def listPersons: java.util.List[Person] = Cypher("START n = node(*) RETURN n")().flatMap { row =>
        val nodeAsMap = row[NeoNode]("n").props
        val jsonValue = Json.toJson(nodeAsMap)(Neo4jREST.mapFormat)
        jsonValue.asOpt[Person]
    }.toList.asJava

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

    def queryPersons(query: Query): java.util.List[Person] = Cypher(
        """
          |START n = node(*)
          |WHERE n.name! =~ {regex}
          |RETURN n;
        """.stripMargin).on("regex" -> query.toRegEx)().flatMap { row =>
        val nodeAsMap = row[NeoNode]("n").props
        val jsonValue = Json.toJson(nodeAsMap)(Neo4jREST.mapFormat)
        jsonValue.asOpt[Person]
    }.toList.asJava

    def queryTeams(query: Query): java.util.List[Team] = Cypher(
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
    }.toList.asJava
}
