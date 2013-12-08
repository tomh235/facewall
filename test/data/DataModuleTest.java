package data;

import domain.Person;
import domain.Team;
import facewall.database.FacewallTestDatabase;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static data.DataModule.createRepository;
import static domain.PersonMatcher.aPerson;
import static domain.PersonsMatcher.arePersons;
import static domain.TeamMatcher.aTeam;
import static domain.TeamsMatcher.areTeams;
import static facewall.database.FacewallTestDatabaseFactory.createImpermanentFacewallTestDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.FixturesFactory.defaultFixtures;
import static facewall.database.fixture.PersonDataFactory.defaultPerson;
import static facewall.database.fixture.PersonDataFactory.defaultPersons;
import static facewall.database.fixture.TeamDataFactory.defaultTeam;
import static org.hamcrest.MatcherAssert.assertThat;

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

        assertThat(result, arePersons().numbering(16));
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

        assertThat(result, arePersons()
            .whichContains(aPerson()
                .named("Earl Grey")
                .withPicture("whittard-earl-grey.png")
                .inTeam(aTeam().named("Teas")))
            .whichContains(aPerson()
                .named("Yorkshire Tea")
                .withPicture("The North.png")
                .inTeam(aTeam().named("Teas")))
            .whichContains(aPerson()
                .named("Gold blend")
                .withPicture("nescafe-gold-blend.img")
                .inTeam(aTeam().named("Coffees")))
        );
    }

    @Test
    public void list_teams_lists_all_teams_in_db() {
        facewallTestDatabase.seedFixtures(newFixtures()
            .withTeams(
                defaultTeam().build(),
                defaultTeam().build(),
                defaultTeam().build()
            ).build()
        );

        List<Team> result = repo.listTeams();

        assertThat(result, areTeams().numbering(3));
    }

    @Test
    public void list_teams_contains_teams_with_data_from_db() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
            .withTeams(
                defaultTeam()
                    .withProperty("name", "Woodwind")
                    .withProperty("colour", "wooden")
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "flute")
                            .build(),
                        defaultPerson()
                            .withProperty("name", "oboe")
                            .build()
                    ).build(),
                defaultTeam()
                    .withProperty("name", "Brass")
                    .withProperty("colour", "metallic")
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "trombone")
                            .build()
                    ).build()
            ).build()
        );

        List<Team> result = repo.listTeams();

        assertThat(result, areTeams()
            .whichContains(aTeam()
                .named("Woodwind")
                .withColour("wooden")
                .whereMembers(arePersons().whichContainExhaustively(
                    aPerson().named("flute"),
                    aPerson().named("oboe")
                )))
            .whichContains(aTeam()
                .named("Brass")
                .withColour("metallic")
                .whereMembers(arePersons().whichContainExhaustively(
                    aPerson().named("trombone")
                ))
        ));
    }
}
