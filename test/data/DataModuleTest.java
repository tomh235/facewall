package data;

import domain.Person;
import facewall.database.FacewallTestDatabase;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static data.DataModule.createRepository;
import static facewall.database.FacewallTestDatabaseFactory.createImpermanentFacewallTestDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.FixturesFactory.defaultFixtures;
import static facewall.database.fixture.PersonDataFactory.defaultPerson;
import static facewall.database.fixture.PersonDataFactory.defaultPersons;
import static facewall.database.fixture.TeamDataFactory.defaultTeam;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DataModuleTest {

    private FacewallTestDatabase facewallTestDatabase;
    private Repository repo;

    @Before
    public void setUp() throws Exception {
        facewallTestDatabase = createImpermanentFacewallTestDatabase();
        repo = createRepository(facewallTestDatabase);
    }

    @Test
    public void list_persons_lists_all_persons_in_db() {
        facewallTestDatabase.seedFixtures(newFixtures()
            .withTeams(
                defaultTeam()
                    .withMembers(defaultPersons(8))
                    .build(),
                defaultTeam()
                    .withMembers(defaultPersons(3))
                    .build(),
                defaultTeam()
                    .withMembers(defaultPersons(5))
                    .build()
            ).build()
        );

        List<Person> result = repo.listPersons();

        assertThat(result.size(), is(16));
    }

    @Test
    public void list_persons_contains_persons_with_data_from_db() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
            .withTeams(
                defaultTeam()
                    .withProperty("name", "Teas")
                    .withMembers(
                        defaultPerson()
                            .withProperties(new HashMap<String, Object>() {{
                                put("name", "Earl Grey");
                                put("picture", "whittard-earl-grey.png");
                            }}).build(),
                        defaultPerson()
                            .withProperties(new HashMap<String, Object>() {{
                                put("name", "Yorkshire Tea");
                                put("picture", "The North.png");
                            }}).build()
                    ).build(),
                defaultTeam()
                    .withProperty("name", "Coffees")
                    .withMembers(
                        defaultPerson()
                            .withProperties(new HashMap<String, Object>() {{
                                put("name", "Gold blend");
                                put("picture", "nescafe-gold-blend.img");
                            }}).build()
                    ).build()
            ).build()
        );

        List<Person> result = repo.listPersons();
    }
}
