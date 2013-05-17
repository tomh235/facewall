package repository

import org.scalatest.FunSuite
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.tg.{TinkerGraph, TinkerGraphFactory}
import com.tinkerpop.gremlin.scala.ScalaGraph

class PersonRepoTest extends FunSuite {

    val repo = PersonRepo()
    
    test("Hugo and Fahran should be in the database.") {

        val scalaGraph = ScalaGraph.wrap(repo.neo4jDB)
        scalaGraph.addV()
        println(scalaGraph.V("name", "Hugo"))
    }

}
