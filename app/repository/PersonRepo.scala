package repository

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph

case class PersonRepo() {
    val neo4jDB = new Neo4jGraph("/opt/neo4j-community-1.8.2/data/graph.db")
}
