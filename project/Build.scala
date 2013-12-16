import com.typesafe.sbtidea.SbtIdeaPlugin
import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName = "facewall"
    val appVersion = "1.0-SNAPSHOT"

    val dependencyResolvers = Seq(
        "maven" at "http://repo1.maven.org/maven2",
        "neo4j" at "http://m2.neo4j.org/content/repositories/releases/",
        "anormcypher" at "http://repo.anormcypher.org/",
        "Mandubian repository snapshots" at "https://github.com/mandubian/mandubian-mvn/raw/master/snapshots/",
        "Mandubian repository releases" at "https://github.com/mandubian/mandubian-mvn/raw/master/releases/",
        "mvnrepository" at "http://mvnrepository.com"
    )

    val facewallDatabaseUtilsDependencies = Seq(
        "org.neo4j" % "neo4j-kernel" % "1.9.5" classifier "tests",
        "org.neo4j" % "neo4j-rest-graphdb" % "1.9",
        "org.neo4j" % "neo4j-cypher" % "1.9.5"
    )

    val facewallDatabaseUtils = Project("facewall-database-utils", file("facewall-database-utils"),
        settings = Defaults.defaultSettings ++
            play.Project.intellijCommandSettings("JAVA") ++
            Seq(
                SbtIdeaPlugin.includeScalaFacet := false,
                libraryDependencies ++= facewallDatabaseUtilsDependencies,
                resolvers ++= dependencyResolvers
            )
    )

    val functionalTestDependencies = Seq(
        "org.neo4j" % "neo4j-kernel" % "1.9.5",
        "org.neo4j" % "neo4j-rest-graphdb" % "1.9",
        "org.seleniumhq.selenium" % "selenium-java" % "2.38.0",
        "org.hamcrest" % "hamcrest-all" % "1.1" % "test",
        "junit" % "junit" % "4.11",
        "org.mockito" % "mockito-all" % "1.9.5"
    )

    val functionalTests = Project("functional-tests", file("functional-tests"),
        settings = Defaults.defaultSettings ++
            play.Project.intellijCommandSettings("JAVA") ++
            Seq(
                SbtIdeaPlugin.includeScalaFacet := false,
                libraryDependencies ++= functionalTestDependencies,
                resolvers ++= dependencyResolvers
            )
    ).dependsOn(facewallDatabaseUtils % "compile->compile;test->test")

    val appDependencies = Seq(
        jdbc,
        anorm,
        javaCore,
        "org.scala-lang" % "scala-compiler" % "2.10.0",
        "com.sun.jersey.contribs" % "jersey-simple-server" % "1.9.1",
        "com.sun.jersey" % "jersey-core" % "1.9",
        "org.neo4j" % "neo4j-rest-graphdb" % "1.9",
        "org.neo4j" % "neo4j-kernel" % "1.9.5",

        "org.hamcrest" % "hamcrest-all" % "1.1" % "test",
        "junit" % "junit" %"4.11" % "test",
        "org.scalatest" %% "scalatest" % "1.9.1" % "test",
        "org.mockito" % "mockito-all" % "1.9.5" % "test",

        //might be able to get rid of this
        "org.neo4j.app" % "neo4j-server" % "1.9",
        "org.anormcypher" %% "anormcypher" % "0.4.1",
        "org.neo4j.app" % "neo4j-server" % "1.9" classifier "static-web"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
        resolvers ++= dependencyResolvers
    ).dependsOn(facewallDatabaseUtils % "test")
}
