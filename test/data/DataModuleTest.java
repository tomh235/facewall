package data;

import domain.Person;
import domain.Query;
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
import static domain.datatype.QueryString.newQueryString;
import static facewall.database.FacewallTestDatabaseFactory.createImpermanentFacewallTestDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.FixturesFactory.defaultFixtures;
import static facewall.database.fixture.PersonDataFactory.defaultPerson;
import static facewall.database.fixture.PersonDataFactory.defaultPersons;
import static facewall.database.fixture.TeamDataFactory.defaultTeam;
import static facewall.database.fixture.TeamDataFactory.defaultTeamWithDefaultMembers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DataModuleTest {

    private FacewallTestDatabase facewallTestDatabase;
    private Repository repo;

    @Before
    public void setUp() throws Exception {
        facewallTestDatabase = createImpermanentFacewallTestDatabase();
        repo = createRepository(facewallTestDatabase.createQueryEngine());
    }

    @Test
    public void list_persons_lists_all_persons_in_db() {
        facewallTestDatabase.seedFixtures(newFixtures()
            .withTeams(
                defaultTeam()
                    .withMembers(defaultPersons(8)),
                defaultTeam()
                    .withMembers(defaultPersons(3)),
                defaultTeam()
                    .withMembers(defaultPersons(5))
            )
        );

        List<Person> result = repo.listPersons();

        assertThat(result, arePersons().numbering(16));
    }

    @Test
    public void list_persons_lists_teamless_persons() {
        facewallTestDatabase.seedFixtures(newFixtures()
            .withTeams(
                defaultTeam()
                    .withMembers(defaultPersons(8)))
            .withTeamlessPersons(
                defaultPersons(2)
            )
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
                            .withProperty("picture", "whittard-earl-grey.png"),
                        defaultPerson()
                            .withProperty("name", "Yorkshire Tea")
                            .withProperty("picture", "The North.png")
                    ),
                defaultTeam()
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "Gold blend")
                            .withProperty("picture", "nescafe-gold-blend.img")
                    )
            )
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
                            .withProperty("name", "Earl Grey"),
                        defaultPerson()
                            .withProperty("name", "Yorkshire Tea")
                    ),
                defaultTeam()
                    .withProperty("name", "coffees")
                    .withMembers(
                        defaultPerson()
                    )
            )
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
                        .withProperty("picture", "important-looking-person1.img"),
                    defaultPerson()
                        .withProperty("name", "delivery manager")
                        .withProperty("picture", "important-looking-person2.img")
                )
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
                defaultTeamWithDefaultMembers(),
                defaultTeamWithDefaultMembers(),
                defaultTeamWithDefaultMembers()
            )
        );

        List<Team> result = repo.listTeams();

        assertThat(result, areTeams().numbering(3));
    }

    @Test
    public void list_teams_contains_teams_with_properties_from_db() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
            .withTeams(
                defaultTeamWithDefaultMembers()
                    .withProperty("name", "Woodwind")
                    .withProperty("colour", "wooden"),
                defaultTeamWithDefaultMembers()
                    .withProperty("name", "Brass")
                    .withProperty("colour", "metallic")
            )
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
                            .withProperty("name", "flute"),
                        defaultPerson()
                            .withProperty("name", "oboe")
                    ),
                defaultTeam()
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "trombone")
                    )
            )
        );

        List<Team> result = repo.listTeams();

        assertThat(result, areTeams()
            .whichContains(aTeam()
                .whereMembers(arePersons()
                    .whichContains(aPerson().named("flute"))
                    .whichContains(aPerson().named("oboe"))
                ))
            .whichContains(aTeam()
                .whereMembers(arePersons()
                    .whichContains(aPerson().named("trombone")
                )))
        );
    }

    @Test
    public void persons_teams_members_contains_itself() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
            .withTeams(
                defaultTeam()
                    .withProperty("name", "woodwind")
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "flute")
                    )
            )
        );

        List<Person> result = repo.listPersons();

        assertThat(result, arePersons()
            .whichContains(aPerson()
                .named("flute")
                .inTeam(aTeam()
                    .named("woodwind")
                    .whereMembers(arePersons()
                        .whichContains(aPerson()
                            .named("flute")
        )))));
    }

    @Test
    public void teams_members_team_is_itself() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
            .withTeams(
                defaultTeam()
                    .withProperty("name", "woodwind")
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "flute")
                    )
            )
        );

        List<Team> result = repo.listTeams();

        assertThat(result, areTeams()
            .whichContains(aTeam()
                .named("woodwind")
                .whereMembers(arePersons()
                    .whichContains(aPerson()
                        .named("flute")
                        .inTeam(aTeam()
                            .named("woodwind")
                        )))));
    }

    @Test
    public void query_teams_for_names_matching_string() {
        facewallTestDatabase.seedFixtures(newFixtures()
            .withTeams(
                defaultTeamWithDefaultMembers()
                    .withProperty("name", "should match this team"),
                defaultTeamWithDefaultMembers()
                    .withProperty("name", "shouldn't match this team"),
                defaultTeamWithDefaultMembers()
                    .withProperty("name", "shouldn't match this team either"),
                defaultTeamWithDefaultMembers()
                    .withProperty("name", "should match this team as well")
            )
        );

        Query query = mock(Query.class);
        when(query.queryString()).thenReturn(newQueryString(".*should match.*"));

        List<Team> result = repo.queryTeams(query);
        assertThat(result, areTeams().whichContainExhaustively(
            aTeam().named("should match this team"),
            aTeam().named("should match this team as well")
        ));
    }
    
    @Test
    public void query_persons_for_name_matching_string() {
        facewallTestDatabase.seedFixtures(newFixtures()
            .withTeams(
                defaultTeamWithDefaultMembers()
                    .withMembers(
                        defaultPerson()
                            .withProperty("name", "rickety doodle"),
                        defaultPerson()
                            .withProperty("name", "poodle do"),
                        defaultPerson()
                            .withProperty("name", "limey tonsil"),
                        defaultPerson()
                            .withProperty("name", "lizard")
            )
        ));

        Query query = mock(Query.class);
        when(query.queryString()).thenReturn(newQueryString(".*oodle.*"));

        List<Person> result = repo.queryPersons(query);
        assertThat(result, arePersons().whichContainExhaustively(
            aPerson().named("rickety doodle"),
            aPerson().named("poodle do")
        ));
    }
}
