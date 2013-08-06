package repository

import domain.Person
import org.anormcypher.{NeoNode, Cypher, Neo4jREST}
import util.JsonConverter

case class PersonRepo(port: Int = 7474) {
    Neo4jREST.setServer("localhost", port)

    def getEveryone: List[Person] = Cypher("START n = node(*) RETURN n;")().flatMap { row =>
        val nodeAsMap = row[NeoNode]("n").props
        val jsonValue = JsonConverter.fromMap(nodeAsMap)
        jsonValue.asOpt[Person]
    }.toList
}
