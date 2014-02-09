package uk.co.o2.facewall.domain;

import uk.co.o2.facewall.domain.datatype.QueryString;

import static uk.co.o2.facewall.domain.datatype.QueryString.newQueryString;

public class Query {

    private static final Query emptyQuery = new Query(newQueryString(""));

    private final QueryString queryString;

    private Query(QueryString queryString) {
        this.queryString = queryString;
    }

    public static Query emptyQuery() {
        return emptyQuery;
    }

    public static Query newCaseSensitiveQuery(String keywords) {
        return new Query(newQueryString(".*" + keywords + ".*"));
    }

    public static Query newCaseInsensitiveQuery(String keywords) {
        return new Query(newQueryString("(?i).*" + keywords + ".*"));
    }

    public static Query newExactQuery(String exactMatch) {
        return new Query(newQueryString(exactMatch));
    }

    public QueryString queryString() {
        return queryString;
    }
}
