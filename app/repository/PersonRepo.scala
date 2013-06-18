package repository

import domain.Person
import org.anormcypher.{NeoNode, Cypher, Neo4jREST}
import org.codehaus.jackson.map.ObjectMapper

case class PersonRepo() {
    Neo4jREST.setServer("localhost", 7474)
    val objectMapper = new ObjectMapper()

    def getEveryone: List[Person] = Cypher("START n = node(*) RETURN n;")().map { row =>
        objectMapper.convertValue[Person](row[NeoNode]("n").props, classOf[Person])
    }.toList
}
