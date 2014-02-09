package uk.co.o2.facewall.data.dao.database;

import uk.co.o2.facewall.data.dao.database.query.DatabaseQuery;
import uk.co.o2.facewall.data.dao.database.query.DatabaseQueryBuilder;
import org.neo4j.rest.graphdb.query.QueryEngine;

import java.util.Map;

public class FacewallDB {

    private final QueryEngine<Map<String, Object>> cypherQueryExecutionEngine;

    public FacewallDB(QueryEngine<Map<String, Object>> cypherQueryExecutionEngine) {
        this.cypherQueryExecutionEngine = cypherQueryExecutionEngine;
    }

    public Iterable<QueryResultRow> query(DatabaseQueryBuilder queryBuilder) {
        DatabaseQuery query = queryBuilder.build();
        return query.execute(cypherQueryExecutionEngine);
    }
}