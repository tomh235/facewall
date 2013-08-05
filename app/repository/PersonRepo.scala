package repository

import domain.Person
import org.anormcypher.{NeoNode, Cypher, Neo4jREST}
import util.MapToJsonConverter

case class PersonRepo() {
    Neo4jREST.setServer("localhost", 7474)

    def getEveryone: List[Person] = Cypher("START n = node(*) RETURN n;")().flatMap { row =>
        val nodeAsMap = row[NeoNode]("n").props
        val jsonValue = MapToJsonConverter.toJson(nodeAsMap)
        jsonValue.asOpt[Person]
    }.toList
}
