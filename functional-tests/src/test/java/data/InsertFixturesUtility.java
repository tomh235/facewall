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
                                ),
                        defaultTeam()
                                .withProperty("name", "OPP Accounts")
                                .withMembers(defaultPerson()
                                        .withProperty("name", "Stuart Gray")
                                        .withProperty("picture", "https://2.gravatar.com/avatar/d225c9bb9f57ac91198c9af48b728d4f?d=https%3A%2F%2Fidenticons.github.com%2Fd052c099b06be7c91c32b0ba900c268c.png&r=x&s=440")
                                ),
                        defaultTeam()
                                .withProperty("name", "Portal Scrum")
                                .withMembers(defaultPerson()
                                        .withProperty("name", "Luke Langfield")
                                        .withProperty("picture", "https://pbs.twimg.com/profile_images/1480549921/Board.jpg")
                                ),
                        defaultTeam()
                                .withProperty("name", "Portal Scrum")
                                .withMembers(defaultPerson()
                                        .withProperty("name", "Charlie Briggs")
                                        .withProperty("picture", "http://withhomeandgarden.com/wp-content/uploads/2011/01/cat-200x300.jpg")
                                )
                )
        );
    }
}
