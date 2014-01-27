package requestmapper;

import domain.Query;
import play.mvc.Http;

import static domain.Query.emptyQuery;
import static domain.Query.newCaseInsensitiveQuery;
import static domain.Query.newCaseSensitiveQuery;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

public class SearchQueryMapper {
    public Query mapCaseSensitively(final Http.Request request) {
        Query query = emptyQuery();

        String[] keywordsQueryParams = request.queryString().get("keywords");
        if (keywordsQueryParams != null) {
            String keywords = keywordsQueryParams[0];

            if (isNotEmpty(keywords)) {
                query = newCaseSensitiveQuery(keywords);
            }
        }
        return query;
    }

    public Query mapCaseInsensitively(final Http.Request request) {
        Query query = emptyQuery();

        String[] keywordsQueryParams = request.queryString().get("keywords");
        if (keywordsQueryParams != null) {
            String keywords = keywordsQueryParams[0];

            if (isNotEmpty(keywords)) {
                query = newCaseInsensitiveQuery(keywords);
            }
        }
        return query;
    }
}