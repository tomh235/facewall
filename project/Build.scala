import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName = "facewall"
    val appVersion = "1.0-SNAPSHOT"

    val appDependencies = Seq(
        // Add your project dependencies here,
        jdbc,
        anorm,
        "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
        "com.michaelpollmeier" % "gremlin-scala" % "2.3.0",
        "com.tinkerpop.blueprints" % "blueprints-neo4j-graph" % "2.3.0"
    )


    val main = play.Project(appName, appVersion, appDependencies).settings(
        // Add your own project settings here
    )

}
