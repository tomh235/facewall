package facade;

import domain.Query;
import org.junit.Test;

import static facade.QueryBuilder.newQuery;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class QueryBuilderTest {

    @Test
    public void builds_query_for_keywords() {
        Query result = newQuery().withKeywords("keywords").build();

        assertThat(result.queryString().value, is(
           ".*keywords.*"
        ));
    }

}
