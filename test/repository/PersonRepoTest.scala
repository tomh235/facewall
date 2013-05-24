package repository

import org.scalatest.FunSuite
import com.tinkerpop.blueprints.impls.tg.{TinkerGraph, TinkerGraphFactory}
import com.tinkerpop.gremlin.scala.ScalaGraph
import domain.Person
import com.tinkerpop.blueprints.Graph

trait InMemoryGraph {
    val hugo = Person("Hugo", "hugo.img")
    val fahran = Person("Fahran", "fahran.img")

    val graph: Graph = {
        val graph = new TinkerGraph()
        graph.addVertex()
        graph.addVertex()
        graph
    }
}

class PersonRepoTest extends FunSuite with InMemoryGraph {

    val repo = PersonRepo(graph)
    
    test("getEveryone should get Hugo and Fahran") {
        val hugo = Person("Hugo", "hugo.img")
        val fahran = Person("Fahran", "fahran.img")
        assert(repo.getEveryone == List(hugo, fahran), "Hugo and Fahran should have been everyone.")
    }
}
