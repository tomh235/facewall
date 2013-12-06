package facewall.database.fixture;

import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.PersonData.newPersonData;
import static facewall.database.fixture.TeamDataFactory.defaultTeam;
import static facewall.database.fixture.TeamDataFactory.defaultTeams;

abstract public class FixturesFactory {

    private FixturesFactory() {}

    public static Fixtures.Builder defaultFixtures() {

        return newFixtures()
            .withTeams(defaultTeams(6));
    }
}
