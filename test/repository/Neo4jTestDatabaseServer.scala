package repository

import javax.ws.rs.{POST, PathParam, GET, Path}
import com.sun.jersey.api.core.DefaultResourceConfig
import com.sun.jersey.simple.container.SimpleServerFactory
import java.io.Closeable
import play.api.libs.json.JsObject
import sys.process._

@Path("/")
class Neo4jTestDatabaseServer {
    val log = new java.io.File("/home/ee/serverlog.txt")

    @GET
    @Path("{id}")
    def get(@PathParam("id") id: String) = {
        s"hello $id"
    }

    @POST
    @Path("{id}")
    def post(@PathParam("id") id: String, body: JsObject) = {
        s"hello $id"
        body.toString() #> log
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