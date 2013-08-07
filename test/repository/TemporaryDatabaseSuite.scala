package repository

import org.neo4j.test.ImpermanentGraphDatabase
import org.neo4j.kernel.GraphDatabaseAPI
import org.neo4j.server.WrappingNeoServerBootstrapper
import org.scalatest.{BeforeAndAfter, FunSuite}


trait TemporaryDatabaseSuite extends FunSuite with BeforeAndAfter {
    class ImpermanentGraphDatabaseAPI() extends ImpermanentGraphDatabase with GraphDatabaseAPI {
        var newConfig = new java.util.HashMap[String, String]()
        newConfig.putAll(config.getParams)
        newConfig.put("ephemeral", "true")
        config.applyChanges(newConfig)
    }

    val graphDb = new ImpermanentGraphDatabaseAPI()
    val bootstrapper = new WrappingNeoServerBootstrapper(graphDb)

    before {
        bootstrapper.start()
    }

    after {
        bootstrapper.stop()
    }
}
