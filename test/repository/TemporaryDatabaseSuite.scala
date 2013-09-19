package repository

import org.neo4j.test.ImpermanentGraphDatabase
import org.neo4j.kernel.GraphDatabaseAPI
import org.neo4j.server.WrappingNeoServerBootstrapper
import scala.collection.mutable
import java.net.ServerSocket
import java.io.IOException
import collection.JavaConversions._
import org.neo4j.server.configuration.{ServerConfigurator, Configurator}
import org.anormcypher.Neo4jREST
import org.scalatest.FunSuite


trait TemporaryDatabaseSuite extends FunSuite {
    class ImpermanentGraphDatabaseAPI() extends ImpermanentGraphDatabase with GraphDatabaseAPI {
        var newConfig = mutable.Map[String, String](config.getParams.toSeq:_*)
        newConfig.put("ephemeral", "true")
        config.applyChanges(newConfig)
    }

    def startNewTestDatabaseRestServerBootstrapper = {
        val graphDb = new ImpermanentGraphDatabaseAPI()
        val config = new ServerConfigurator(graphDb)
        val port = findFreePort(Range(49999, 65535))
        config.configuration().addProperty(Configurator.WEBSERVER_PORT_PROPERTY_KEY, port)
        val bootstrapper = new WrappingNeoServerBootstrapper(graphDb, config)
        bootstrapper.start()
        Neo4jREST.setServer("localhost", port)
        bootstrapper
    }

    private def findFreePort(portRange: Range): Int = {
        portRange.find { prospectivePort: Int =>
            try {
                val socket = new ServerSocket(prospectivePort)
                socket.close()
                true
            } catch {
                case _: IOException => false
            }
        }.getOrElse( throw new IOException("No free port found for Test Database"))
    }
}
