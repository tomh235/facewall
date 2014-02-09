package uk.co.o2.facewall.data.dao.database.query;

import org.junit.Test;

import static uk.co.o2.facewall.data.dao.database.DatabaseQueryBuilderMatcher.aDatabaseQueryBuilder;
import static uk.co.o2.facewall.data.dao.database.DatabaseQueryMatchers.*;
import static uk.co.o2.facewall.data.datatype.PersonId.newPersonId;
import static uk.co.o2.facewall.domain.datatype.QueryString.newQueryString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

public class PersonDatabaseQueryBuilderTest {

    private final DatabaseQueryFactory databaseQueryFactory = new DatabaseQueryFactory(mock(FacewallQueryResultsMapper.class));

    @Test
    public void forPersons_builds_query_for_all_persons() {
        PersonDatabaseQueryBuilder result = databaseQueryFactory.forPersons();

        assertThat(result, is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForAllPersons())
        ));
    }

    @Test
    public void builds_query_for_person_with_id() {
        PersonDatabaseQueryBuilder result = databaseQueryFactory.forPersons().withId(newPersonId("person-id"));

        assertThat(result, is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForPersonWithId("person-id"))
        ));
    }

    @Test
    public void builds_query_for_persons_named_george() {
        PersonDatabaseQueryBuilder result = databaseQueryFactory.forPersons().named(newQueryString(".*George.*"));

        assertThat(result, is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForPersonsNamed(".*George.*"))
        ));
    }
}
