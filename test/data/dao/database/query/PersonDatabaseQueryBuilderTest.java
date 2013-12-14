package data.dao.database.query;

import org.junit.Test;

import static data.dao.database.DatabaseQueryBuilderMatcher.aDatabaseQueryBuilder;
import static data.dao.database.DatabaseQueryMatchers.aQueryForAllPersons;
import static data.dao.database.query.PersonDatabaseQueryBuilder.forPersons;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PersonDatabaseQueryBuilderTest {

    @Test
    public void forPersons_builds_query_for_all_persons() {
        PersonDatabaseQueryBuilder result = forPersons();

        assertThat(result, is(aDatabaseQueryBuilder()
            .whichBuilds(aQueryForAllPersons())
        ));
    }

}
