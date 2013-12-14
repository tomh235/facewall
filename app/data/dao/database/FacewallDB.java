package data.dao.database;

import data.dao.database.query.DatabaseQuery;
import data.dao.database.query.DatabaseQueryBuilder;
import org.neo4j.rest.graphdb.query.QueryEngine;

import java.util.Map;

public class FacewallDB {
    public enum NodeIndex {

        Persons("Persons", "id"),
        Teams("Teams", "id");

        private final String name;
        private final String key;

        private NodeIndex(String name, String key) {
            this.name = name;
            this.key = key;
        }

        public final String getName() {
            return name;
        }

        public final String getKey() {
            return key;
        }
    }

    private final QueryEngine<Map<String, Object>> cypherQueryExecutionEngine;

    public FacewallDB(QueryEngine<Map<String, Object>> cypherQueryExecutionEngine) {
        this.cypherQueryExecutionEngine = cypherQueryExecutionEngine;
    }

    public Iterable<QueryResultRow> query(DatabaseQueryBuilder queryBuilder) {
        DatabaseQuery query = queryBuilder.build();
        return query.execute(cypherQueryExecutionEngine);
    }
}