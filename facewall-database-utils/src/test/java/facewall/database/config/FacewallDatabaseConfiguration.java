package facewall.database.config;

import org.neo4j.graphdb.RelationshipType;

import static org.neo4j.graphdb.DynamicRelationshipType.withName;

public class FacewallDatabaseConfiguration {

    public final static RelationshipType MEMBER_OF = withName("TEAMMEMBER_OF");
    public final static String personIndexName = "Persons";
    public final static String teamIndexName = "Teams";
    public final static String indexKey = "";
}
