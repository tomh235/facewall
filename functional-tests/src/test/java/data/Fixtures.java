package data;

import org.neo4j.graphdb.GraphDatabaseService;

/*

This interface isn't really at all close to it's final form; it should also provide a view into the data that gets uploaded
into the database. This is the primary means by which tests will communicate to the database, so it must be understandable
by both the database and tests.

I am envisioning our functional tests to look something like the following:

fixtures = FixturesFactory.fixturesBuilder(). (fluent API for defining fixtures used in the test) .build()
DatabaseUtils.seedFixtures(fixtures)

(Use selenium web driver to navigate to the appropriate page)

assertThat(page, matchesExpectations())

where the expectations are defined in terms of the fixtures that were seeded into the database.

*/
public interface Fixtures {
    public void seedDatabase(GraphDatabaseService db);
}
