package facewall.database.config;

import org.neo4j.graphdb.RelationshipType;

import static org.neo4j.graphdb.DynamicRelationshipType.withName;

public class FacewallDatabaseConfiguration {

    public final static RelationshipType MEMBER_OF = withName("TEAMMEMBER_OF");

    public final static IndexConfiguration Persons_Id = new IndexConfiguration("Persons_Id", "id");
    public final static IndexConfiguration Teams_Id = new IndexConfiguration("Teams_Id", "id");
}
