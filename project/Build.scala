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
        "org.anormcypher" %% "anormcypher" % "0.4.1"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
        resolvers ++= Seq(
            "maven"             at "http://repo1.maven.org/maven2",
            "anormcypher" at "http://repo.anormcypher.org/",
            "Mandubian repository snapshots" at "https://github.com/mandubian/mandubian-mvn/raw/master/snapshots/",
            "Mandubian repository releases" at "https://github.com/mandubian/mandubian-mvn/raw/master/releases/"
        )
    )

}
