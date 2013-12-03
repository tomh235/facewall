package requestmapper;

import domain.Query;

public class QueryMapper {
    public Query map(final String queryString) {
        return new Query() {
            @Override
            public String toRegEx() {
                return queryString;
            }
        };
    }
}
