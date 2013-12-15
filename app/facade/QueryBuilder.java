package facade;

import domain.Query;
import domain.datatype.QueryString;

import static domain.datatype.QueryString.newQueryString;

public class QueryBuilder {

    private String keywords;

    private QueryBuilder() {}

    public static QueryBuilder newQuery() {
        return new QueryBuilder();
    }

    public Query build() {
        return new Query() {
            final QueryString queryString = newQueryString(".*" + keywords + ".*");

            @Override public QueryString queryString() {
                return queryString;
            }
        };
    }

    public QueryBuilder withKeywords(String keywords) {
        this.keywords = keywords;
        return this;
    }
}
