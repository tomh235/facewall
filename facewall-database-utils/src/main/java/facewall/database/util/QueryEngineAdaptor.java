package facewall.database.util;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.rest.graphdb.query.QueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;
import org.neo4j.rest.graphdb.util.QueryResultBuilder;

import java.util.Map;

public class QueryEngineAdaptor implements QueryEngine<Map<String, Object>> {

    private final ExecutionEngine engine;

    private QueryEngineAdaptor(ExecutionEngine engine) {
        this.engine = engine;
    }

    public static QueryEngine<Map<String, Object>> createQueryEngineAdaptor(ExecutionEngine executionEngine) {
        return new QueryEngineAdaptor(executionEngine);
    }

    @Override public QueryResult<Map<String, Object>> query(String statement, Map<String, Object> params) {
        ExecutionResult result = engine.execute(statement, params);

        return new QueryResultBuilder<>(result);
    }
}
