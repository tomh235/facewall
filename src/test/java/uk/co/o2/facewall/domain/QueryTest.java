package uk.co.o2.facewall.domain;

import org.junit.Test;

import static uk.co.o2.facewall.domain.Query.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class QueryTest {

    @Test
    public void case_sensitive_query_for_keywords() {
        Query result = newCaseSensitiveQuery("keywords");

        assertThat(result.queryString().value, is(
           ".*keywords.*"
        ));
    }

    @Test
    public void case_insensitive_query_for_keywords() {
        Query result = newCaseInsensitiveQuery("keywords");

        assertThat(result.queryString().value, is(
                "(?i).*keywords.*"
        ));
    }

    @Test
    public void empty_query() {
        Query result = emptyQuery();

        assertThat(result.queryString().value, is(
           ""
        ));
    }
}
