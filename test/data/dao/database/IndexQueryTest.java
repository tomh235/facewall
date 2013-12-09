package data.dao.database;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import static data.dao.database.IndexQuery.anIndexLookupForPersons;
import static data.dao.database.IndexQuery.anIndexLookupForTeams;
import static data.dao.database.IndexQueryMatcher.anIndexQuery;
import static data.dao.database.PersonNodeIndex.Persons_Id;
import static data.dao.database.TeamNodeIndex.Teams_Id;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IndexQueryTest {

    private static final TeamNodeIndex someIndex = Teams_Id;

    @Test(expected = IllegalStateException.class)
    public void builder_enforces_requirement_that_an_index_is_specified() {
        anIndexLookupForPersons().build();
    }

    @Test
    public void query_on_index_retrieves_indexed_key() {
        IndexQuery indexQuery = anIndexLookupForPersons()
            .onIndex(Persons_Id)
            .build();

        assertThat(indexQuery, is(anIndexQuery()
            .queryingOnTheKey(Persons_Id.getKey())
        ));
    }

    @Test
    public void query_on_index_retrieves_index_name() {
        IndexQuery indexQuery = anIndexLookupForPersons()
            .onIndex(Persons_Id)
            .build();

        assertThat(indexQuery, is(anIndexQuery()
            .queryingOnAnIndexNamed(Persons_Id.getName())
        ));
    }

    @Test
    public void query_on_index_for_value() {
        IndexQuery indexQuery = anIndexLookupForTeams()
            .onIndex(someIndex)
            .forValue("value")
            .build();

        assertThat(indexQuery, is(anIndexQuery()
            .queryingForTheValue("value")
        ));
    }

    @Test
    public void query_on_index_for_all_values() {
        IndexQuery indexQuery = anIndexLookupForTeams()
            .onIndex(someIndex)
            .forAllValues()
            .build();

        assertThat(indexQuery, is(anIndexQuery()
            .queryingForAllValues()
        ));
    }
}
