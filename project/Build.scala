import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName = "facewall"
    val appVersion = "1.0-SNAPSHOT"

    val appDependencies = Seq(
        jdbc,
        anorm,
        "org.scala-lang" % "scala-compiler" % "2.10.0",
        "org.anormcypher" %% "anormcypher" % "0.4.1",
        "org.neo4j.app" % "neo4j-server" % "1.9",
        "com.sun.jersey.contribs" % "jersey-simple-server" % "1.9.1",
        "com.sun.jersey" % "jersey-core" % "1.9",

        "org.hamcrest" % "hamcrest-all" % "1.1" % "test",
        "org.scalatest" %% "scalatest" % "1.9.1" % "test",
        "org.mockito" % "mockito-core" % "1.9.5" % "test",
      "org.neo4j" % "neo4j-kernel" % "1.9",
        "org.neo4j.app" % "neo4j-server" % "1.9" classifier "static-web"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
        resolvers ++= Seq(
            "maven" at "http://repo1.maven.org/maven2",
            "anormcypher" at "http://repo.anormcypher.org/",
            "Mandubian repository snapshots" at "https://github.com/mandubian/mandubian-mvn/raw/master/snapshots/",
            "Mandubian repository releases" at "https://github.com/mandubian/mandubian-mvn/raw/master/releases/",
            "mvnrepository" at "http://mvnrepository.com"
        )
    )

}
