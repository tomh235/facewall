package uk.co.o2.facewall.data.dao.database;

import java.util.ArrayList;
import java.util.List;

public class QueryResultsBuilder {

    List<QueryResultRow> rows = new ArrayList<>();

    private QueryResultsBuilder() {}

    public static QueryResultsBuilder results() {
        return new QueryResultsBuilder();
    }

    public QueryResultsBuilder withRows(QueryResultRowBuilder... rowBuilders) {
        for (QueryResultRowBuilder rowBuilder : rowBuilders) {
            rows.add(rowBuilder.build());
        }
        return this;
    }

    public Iterable<QueryResultRow> build() {
        return rows;
    }
}
