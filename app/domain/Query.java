package domain;

import domain.datatype.QueryString;

import static domain.datatype.QueryString.newQueryString;

public class Query {

    private static final Query emptyQuery = new Query(newQueryString(""));

    private final QueryString queryString;

    private Query(QueryString queryString) {
        this.queryString = queryString;
    }

    public static Query emptyQuery() {
        return emptyQuery;
    }

    public static Query newQuery(String keywords) {
        return new Query(newQueryString(".*" + keywords + ".*"));
    }

    public static Query newExactQuery(String exactMatch) {
        return new Query(newQueryString(exactMatch));
    }

    public QueryString queryString() {
        return queryString;
    }
}
