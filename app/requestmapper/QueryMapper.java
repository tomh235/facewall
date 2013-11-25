package requestmapper;

import domain.Query;
import play.mvc.Http;

public class QueryMapper {
    public Query map(Http.Request request) {

        final String keywords = request.queryString().get("keywords")[0];
        if(keywords.equals("")) {
            return new Query() {
                @Override
                public String toRegEx() {
                    return "";
                }
            };
        }
        else {
            return new Query() {
               @Override
               public String toRegEx() {
                    return String.format("(?i).*%s.*", keywords);
                }
            };
        }

    }
}
