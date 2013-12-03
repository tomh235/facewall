package requestmapper;

import domain.Query;
import org.junit.Test;

import static domain.QueryMatcher.aQuery;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class QueryMapperTest {
    QueryMapper QueryMapper = new QueryMapper();

    @Test
    public void should_map_teamName_query_to_regex() {
        Query result = QueryMapper.map("test");
        assertThat(result, is(aQuery().withRegEx("test")));
    }
}
