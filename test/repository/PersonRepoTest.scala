package repository

import org.scalatest.FunSuite
import domain.Person

trait InMemoryGraph {
    val hugo = Person("Hugo", "hugo.img")
    val fahran = Person("Fahran", "fahran.img")


    val graph = new TestGraphDatabaseFactory().newImpermanentDatabase()
}

class PersonRepoTest extends FunSuite with InMemoryGraph {

    val repo = PersonRepo(graph)

    test("getEveryone should get Hugo and Fahran") {
        val hugo = Person("Hugo", "hugo.img")
        val fahran = Person("Fahran", "fahran.img")
        assert(repo.getEveryone == List(hugo, fahran), "Hugo and Fahran should have been everyone.")
    }
}
