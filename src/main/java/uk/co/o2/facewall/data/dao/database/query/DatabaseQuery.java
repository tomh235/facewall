package uk.co.o2.facewall.data.dao.database.query;

import uk.co.o2.facewall.data.dao.database.QueryResultRow;
import org.neo4j.rest.graphdb.query.QueryEngine;

import java.util.Map;

public interface DatabaseQuery {

    Iterable<QueryResultRow> execute(QueryEngine<Map<String, Object>> queryEngine);
}
