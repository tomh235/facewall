package repository

import org.scalatest.FunSuite
import domain.Person
import org.specs2.specification.BeforeAfter
import org.neo4j.test.{ImpermanentGraphDatabase, TestGraphDatabaseFactory}
import org.neo4j.server.WrappingNeoServerBootstrapper

trait InMemoryGraph {
    val hugo = Person("Hugo", "hugo.img")
    val fahran = Person("Fahran", "fahran.img")

    def graphDb = new ImpermanentGraphDatabase("test-data/impermanent-db")
}

class PersonRepoTest extends FunSuite with InMemoryGraph with BeforeAfter {

    val repo = PersonRepo()
    var dbWrappingServer: WrappingNeoServerBootstrapper = null

    def after: Any = {
        dbWrappingServer.stop()
    }

    def before: Any = {
        dbWrappingServer = new WrappingNeoServerBootstrapper(graphDb)
        dbWrappingServer.start()
    }   

    test("getEveryone should get Hugo and Fahran") {
        val hugo = Person("Hugo", "hugo.img")
        val fahran = Person("Fahran", "fahran.img")
        assert(repo.getEveryone == List(hugo, fahran), "Hugo and Fahran should have been everyone.")
    }

}
