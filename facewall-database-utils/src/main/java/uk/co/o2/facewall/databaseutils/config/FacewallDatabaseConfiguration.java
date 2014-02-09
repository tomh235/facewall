package uk.co.o2.facewall.databaseutils.config;

import org.neo4j.graphdb.RelationshipType;

import static org.neo4j.graphdb.DynamicRelationshipType.withName;

public class FacewallDatabaseConfiguration {

    public final static RelationshipType MEMBER_OF = withName("TEAMMEMBER_OF");

    public enum IndexConfiguration {
        Persons("Persons", "id"),
        Teams("Teams", "id");

        public final String indexName;
        public final String key;

        private IndexConfiguration(String indexName, String key) {
            this.indexName = indexName;
            this.key = key;
        }
    }
}
