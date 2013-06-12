package repository

import com.tinkerpop.blueprints.{Vertex, Graph}
import domain.Person
import com.tinkerpop.gremlin.scala.ScalaGraph
import org.anormcypher.{Cypher, Neo4jREST}

case class PersonRepo() {
    Neo4jREST.setServer("localhost", 7474)
    def getEveryone: List[Person] = {
      val cypher = Cypher("START n = node(*) RETURN n;")

    }
}
