package facewall.database.config;

import org.neo4j.graphdb.RelationshipType;

import static org.neo4j.graphdb.DynamicRelationshipType.withName;

public class FacewallDatabaseConfiguration {

    public final static RelationshipType MEMBER_OF = withName("TEAMMEMBER_OF");

    public enum IndexConfiguration {
        Persons_Id("Persons_Id", "id"),
        Persons_Name("Persons_Name", "name"),
        Teams_Id("Teams_Id", "id"),
        Teams_Name("Teams_Name", "name");

        public final String indexName;
        public final String key;

        private IndexConfiguration(String indexName, String key) {
            this.indexName = indexName;
            this.key = key;
        }
    }
}
