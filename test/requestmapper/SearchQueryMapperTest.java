package requestmapper;

import domain.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import play.mvc.Http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static domain.QueryMatcher.aQuery;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchQueryMapperTest {
    @Mock Http.Request request;
    SearchQueryMapper searchQueryMapper = new SearchQueryMapper();

    @Test
    public void should_map_no_query_params_into_wildcard_regex() {
        Map<String, String[]> queryString = Collections.emptyMap();
        when(request.queryString()).thenReturn(queryString);

        Query result = searchQueryMapper.map(request);

        assertThat(result, is(aQuery().whichIsEmpty()));
    }

    @Test
    public void should_map_a_request_with_no_keywords_into_empty_regex() {
        Map<String, String[]> queryString = new HashMap<String, String[]>() {{
            put("keywords", new String[]{""});
        }};
        when(request.queryString()).thenReturn(queryString);

        Query result = searchQueryMapper.map(request);

        assertThat(result, is(aQuery().whichIsEmpty()));
    }

    @Test
    public void should_map_from_request_to_query_regex() {
        Map<String, String[]> queryString = new HashMap<String, String[]>() {{
            put("keywords", new String[]{"hello"});
        }};
        when(request.queryString()).thenReturn(queryString);

        Query result = searchQueryMapper.map(request);

        assertThat(result, is(aQuery().withQueryString(".*hello.*")));
    }
}