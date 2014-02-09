package uk.co.o2.facewall.databaseutils.fixture;

import static uk.co.o2.facewall.databaseutils.fixture.Fixtures.newFixtures;
import static uk.co.o2.facewall.databaseutils.fixture.PersonDataFactory.defaultPersons;
import static uk.co.o2.facewall.databaseutils.fixture.TeamDataFactory.defaultTeams;

abstract public class FixturesFactory {

    private FixturesFactory() {}

    public static Fixtures.Builder defaultFixtures() {

        return newFixtures()
            .withTeams(defaultTeams(6))
            .withTeamlessPersons(defaultPersons(3));
    }
}
