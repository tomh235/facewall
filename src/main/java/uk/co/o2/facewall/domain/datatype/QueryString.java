package uk.co.o2.facewall.domain.datatype;

import uk.co.o2.facewall.util.AbstractWrappingDataType;

public class QueryString extends AbstractWrappingDataType<String> {
    private QueryString(String value) {
        super(value);
    }

    public static QueryString newQueryString(String queryString) {
        return new QueryString(queryString);
    }
}
