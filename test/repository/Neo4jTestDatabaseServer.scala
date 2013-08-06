package repository

import javax.ws.rs._
import com.sun.jersey.api.core.DefaultResourceConfig
import com.sun.jersey.simple.container.SimpleServerFactory
import java.io.{File, Closeable}
import play.api.libs.json.JsObject
import sys.process._
import play.api.libs.json.JsObject
import javax.ws.rs.core.MediaType

@Path("/")
class Neo4jTestDatabaseServer {

    @GET
    @Path("{id}")
    def get(@PathParam("id") id: String) = {
        s"hello $id"
    }

    @POST
    @Path("{id}")
    def post(@PathParam("id") id: String, body: String) = {
        """
          |{
          |  "columns" : [ "type(r)", "n.name?", "n.age?" ],
          |  "data" : [ [ "know", "him", 25 ], [ "know", "you", null ] ]
          |}
        """.stripMargin
    }
}

object Neo4jTestDatabaseServer {
    var server: Closeable = _

    def start() {
        val resourceConfig = new DefaultResourceConfig(classOf[Neo4jTestDatabaseServer])
        server = SimpleServerFactory.create("http://0.0.0.0:5555", resourceConfig)
    }

    def stop() {
        server.close()
    }
}