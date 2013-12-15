package requestmapper;

import domain.Query;
import domain.datatype.QueryString;
import play.mvc.Http;

import static domain.datatype.QueryString.newQueryString;

public class SearchQueryMapper {
    public Query map(Http.Request request) {
        // TODO -- Charlie : Implement a better way of handling GET requests to /search-results with wrong parameters
        if (request.queryString().get("keywords") == null) {
            return new Query() {
                @Override
                public QueryString queryString() {
                    return newQueryString(".*");
                }
            };
        }
        final String keywords = request.queryString().get("keywords")[0];
        if (keywords.equals("")) {
            return new Query() {
                @Override
                public QueryString queryString() {
                    return newQueryString("");
                }
            };
        } else {
            //this could do with a comment <-- not this one, obviously
            return new Query() {
                @Override
                public QueryString queryString() {
                    return newQueryString(String.format("(?i).*%s.*", keywords));
                }
            };
        }
    }
}
