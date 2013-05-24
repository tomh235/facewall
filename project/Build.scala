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
        "org.scala-lang" % "scala-compiler" % "2.10.0",
        "org.scalatest" %% "scalatest" % "1.9.1" % "test",
        "org.neo4j" % "neo4j-scala" % "0.2.0-M2-SNAPSHOT"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
        resolvers ++= Seq(
            "deneb"             at "http://deneb.dev.o2.co.uk:8081/nexus/content/groups/public",
            "deneb-third-party" at "http://deneb.dev.o2.co.uk:8081/nexus/content/repositories/thirdparty",
            "maven"             at "http://repo1.maven.org/maven2",
            "fakod-releases"    at "https://raw.github.com/FaKod/fakod-mvn-repo/master/releases",
            "fakod-snapshots"   at "https://raw.github.com/FaKod/fakod-mvn-repo/master/snapshots"

            //TODO: Close, but no cigar. FaKod's mvn repo doesn't contain a copy of a dependency (neo4j-rest-graphdb;1.8) Should probably upload a copy of everything to our nexus.
        )
    )

}
