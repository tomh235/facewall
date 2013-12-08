package data;

import domain.Person;
import domain.Team;
import facewall.database.FacewallTestDatabase;
import org.junit.Before;
import org.junit.Test;

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
    public void list_persons_lists_teamless_persons() {
        facewallTestDatabase.seedFixtures(newFixtures()
            .withTeams(
                defaultTeam()
                    .withMembers(defaultPersons(8))
                    .build())
            .withTeamlessPersons(
                defaultPersons(2)
            ).build()
        );

        List<Person> result = repo.listPersons();

        assertThat(result, arePersons().numbering(10));
    }

    @Test
    public void list_persons_contains_persons_with_data_from_db() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
            .withTeams(
                defaultTeam()
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "Earl Grey")
                            .withProperty("picture", "whittard-earl-grey.png")
                            .build(),
                        defaultPerson()
                            .withProperty("name", "Yorkshire Tea")
                            .withProperty("picture", "The North.png")
                            .build()
                    ).build(),
                defaultTeam()
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "Gold blend")
                            .withProperty("picture", "nescafe-gold-blend.img")
                            .build()
                    ).build()
            ).build()
        );

        List<Person> result = repo.listPersons();

        assertThat(result, arePersons()
            .whichContains(aPerson()
                .named("Earl Grey")
                .withPicture("whittard-earl-grey.png"))
            .whichContains(aPerson()
                .named("Yorkshire Tea")
                .withPicture("The North.png"))
            .whichContains(aPerson()
                .named("Gold blend")
                .withPicture("nescafe-gold-blend.img"))
        );
    }

    @Test
    public void list_persons_contains_persons_in_teams_from_db() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
            .withTeams(
                defaultTeam()
                    .withProperty("name", "teas")
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "Earl Grey")
                            .build(),
                        defaultPerson()
                            .withProperty("name", "Yorkshire Tea")
                            .build()
                    ).build(),
                defaultTeam()
                    .withProperty("name", "coffees")
                    .withMembers(
                        defaultPerson().build()
                    ).build()
            ).build()
        );

        List<Person> result = repo.listPersons();

        assertThat(result, arePersons()
            .whichContains(aPerson()
                .named("Earl Grey")
                .inTeam(aTeam().named("teas")))
            .whichContains(aPerson()
                .named("Yorkshire Tea")
                .inTeam(aTeam().named("teas")))
            .whichContains(aPerson()
                .inTeam(aTeam().named("coffees")))
        );
    }

    @Test
    public void list_persons_contains_teamless_persons_with_data_from_db() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
                .withTeamlessPersons(
                    defaultPerson()
                        .withProperty("name", "development manager")
                        .withProperty("picture", "important-looking-person1.img")
                        .build(),
                    defaultPerson()
                        .withProperty("name", "delivery manager")
                        .withProperty("picture", "important-looking-person2.img")
                        .build()
                ).build()
        );

        List<Person> result = repo.listPersons();

        assertThat(result, arePersons()
            .whichContains(aPerson()
                .named("development manager")
                .withPicture("important-looking-person1.img")
                .whoIsNotInATeam())
            .whichContains(aPerson()
                .named("delivery manager")
                .withPicture("important-looking-person2.img")
                .whoIsNotInATeam())
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
    public void list_teams_contains_teams_with_properties_from_db() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
            .withTeams(
                defaultTeam()
                    .withProperty("name", "Woodwind")
                    .withProperty("colour", "wooden")
                    .build(),
                defaultTeam()
                    .withProperty("name", "Brass")
                    .withProperty("colour", "metallic")
                    .build()
            ).build()
        );

        List<Team> result = repo.listTeams();

        assertThat(result, areTeams()
            .whichContains(aTeam()
                .named("Woodwind")
                .withColour("wooden"))
            .whichContains(aTeam()
                .named("Brass")
                .withColour("metallic"))
        );
    }

    @Test
    public void list_teams_contains_teams_with_members_from_db() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
            .withTeams(
                defaultTeam()
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "flute")
                            .build(),
                        defaultPerson()
                            .withProperty("name", "oboe")
                            .build()
                    ).build(),
                defaultTeam()
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
                .whereMembers(arePersons().whichContainExhaustively(
                    aPerson().named("flute"),
                    aPerson().named("oboe")
                )))
            .whichContains(aTeam()
                .whereMembers(arePersons().whichContainExhaustively(
                    aPerson().named("trombone")
                )))
        );
    }
}
