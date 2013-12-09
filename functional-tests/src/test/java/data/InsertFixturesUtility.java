package data;

import facewall.database.FacewallTestDatabase;
import org.neo4j.graphdb.GraphDatabaseService;

import static facewall.database.FacewallTestDatabaseFactory.createFacewallTestDatabaseWrappingExistingDatabase;
import static facewall.database.fixture.FixturesFactory.defaultFixtures;
import static facewall.database.fixture.PersonDataFactory.defaultPerson;
import static facewall.database.fixture.TeamDataFactory.defaultTeam;
import static org.neo4j.rest.graphdb.GraphDatabaseFactory.databaseFor;

public class InsertFixturesUtility {

    private final static String dbLocation = "http://localhost:7474/db/data";

    public static void main(String[] args) {
        GraphDatabaseService neoDb = databaseFor(dbLocation);
        FacewallTestDatabase facewallDb = createFacewallTestDatabaseWrappingExistingDatabase(neoDb);

        facewallDb.clear();
        facewallDb.initialise();

        facewallDb.seedFixtures(defaultFixtures()
            .withTeams(
                defaultTeam()
                    .withProperty("name", "Ecom Shop")
                    .withMembers(defaultPerson()
                        .withProperty("name", "Hugo Wainwright")
                        .withProperty("picture", "https://fbcdn-sphotos-c-a.akamaihd.net/hphotos-ak-frc1/300430_4471674470606_1745866994_n.jpg")
                    ),
                defaultTeam()
                    .withProperty("name", "Ecom Ars")
                    .withMembers(defaultPerson()
                        .withProperty("name", "Fahran Wallace")
                        .withProperty("picture", "http://withhomeandgarden.com/wp-content/uploads/2011/01/cat-200x300.jpg")
                    )
            )
        );
    }
}
