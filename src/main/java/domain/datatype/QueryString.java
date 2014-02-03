package domain.datatype;

import util.AbstractWrappingDataType;

public class QueryString extends AbstractWrappingDataType<String> {
    private QueryString(String value) {
        super(value);
    }

    public static QueryString newQueryString(String queryString) {
        return new QueryString(queryString);
    }
}
