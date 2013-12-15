package requestmapper;

import domain.Query;
import domain.datatype.QueryString;

import static domain.datatype.QueryString.newQueryString;

public class QueryMapper {
    public Query map(final String queryString) {
        return new Query() {
            @Override
            public QueryString queryString() {
                return newQueryString(".*" + queryString + ".*");
            }
        };
    }
}
