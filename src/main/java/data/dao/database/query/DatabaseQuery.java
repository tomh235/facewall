package data.dao.database.query;

import data.dao.database.QueryResultRow;
import org.neo4j.rest.graphdb.query.QueryEngine;

import java.util.Map;

public interface DatabaseQuery {

    Iterable<QueryResultRow> execute(QueryEngine<Map<String, Object>> queryEngine);
}
