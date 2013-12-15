package requestmapper;

import domain.Query;
import domain.datatype.QueryString;
import play.mvc.Http;

import static domain.Query.emptyQuery;
import static domain.Query.newQuery;
import static domain.datatype.QueryString.newQueryString;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

public class SearchQueryMapper {
    public Query map(final Http.Request request) {
        Query query = emptyQuery();

        String[] keywordsQueryParams = request.queryString().get("keywords");
        if (keywordsQueryParams != null) {
            String keywords = keywordsQueryParams[0];

            if (isNotEmpty(keywords)) {
                query = newQuery(keywords);
            }
        }
        return query;
    }
}