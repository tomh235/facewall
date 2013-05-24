package repository

import com.tinkerpop.blueprints.{Vertex, Graph}
import domain.Person
import com.tinkerpop.gremlin.scala.ScalaGraph

case class PersonRepo(graph: Graph) {
    val scalaGraph = ScalaGraph.wrap(graph)
    def getEveryone: List[Person] = ???
}
