package requestmapper;

import domain.Query;
import domain.datatype.QueryString;
import org.apache.commons.lang.StringUtils;
import play.mvc.Http;

import static domain.datatype.QueryString.newQueryString;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

public class SearchQueryMapper {
    public Query map(final Http.Request request) {
        return new Query() {
            @Override public QueryString queryString() {
                QueryString queryString = newQueryString("");

                String[] keywordsQueryParams = request.queryString().get("keywords");
                if (keywordsQueryParams != null) {
                    String keywords = keywordsQueryParams[0];

                    if (isNotEmpty(keywords)) {
                        queryString = newQueryString(".*" + keywords + ".*");
                    }
                }
                return queryString;
            }
        };
    }
}
