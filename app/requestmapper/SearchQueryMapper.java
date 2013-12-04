package requestmapper;

import domain.Query;
import play.mvc.Http;

public class SearchQueryMapper {
    public Query map(Http.Request request) {
        // TODO -- Charlie : Implement a better way of handling GET requests to /search-results with wrong parameters
        if (request.queryString().get("keywords") == null) {
            return new Query() {
                @Override
                public String toRegEx() {
                    return ".*";
                }
            };
        }
        final String keywords = request.queryString().get("keywords")[0];
        if (keywords.equals("")) {
            return new Query() {
                @Override
                public String toRegEx() {
                    return "";
                }
            };
        } else {
            return new Query() {
                @Override
                public String toRegEx() {
                    return String.format("(?i).*%s.*", keywords);
                }
            };
        }
    }
}
