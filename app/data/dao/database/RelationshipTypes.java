package data.dao.database;

import org.neo4j.graphdb.RelationshipType;

public enum RelationshipTypes implements RelationshipType {
    IN_TEAM,
    IN_SCRUM,
    IN_LOCATION,
    HAS_ROLE
}
