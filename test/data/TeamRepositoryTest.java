package data;

import domain.Query;
import domain.Team;
import facewall.database.FacewallTestDatabase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static data.DataModule.createDataModule;
import static domain.PersonMatcher.aPerson;
import static domain.PersonsMatcher.arePersons;
import static domain.TeamMatcher.aTeam;
import static domain.TeamsMatcher.areTeams;
import static domain.datatype.QueryString.newQueryString;
import static facewall.database.FacewallTestDatabaseFactory.createImpermanentFacewallTestDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.FixturesFactory.defaultFixtures;
import static facewall.database.fixture.PersonDataFactory.defaultPerson;
import static facewall.database.fixture.TeamDataFactory.defaultTeam;
import static facewall.database.fixture.TeamDataFactory.defaultTeamWithDefaultMembers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TeamRepositoryTest {

    private FacewallTestDatabase facewallTestDatabase;
    private TeamRepository repo;

    @Before
    public void setUp() throws Exception {
        facewallTestDatabase = createImpermanentFacewallTestDatabase();
        repo = createDataModule(facewallTestDatabase.createQueryEngine()).teamRepository;
    }

    @Test
    public void list_teams_retrieves_team_names_from_db() {
        facewallTestDatabase.seedFixtures(newFixtures()
                .withTeams(
                        defaultTeamWithDefaultMembers()
                                .withProperty("name", "Ecom"),
                        defaultTeamWithDefaultMembers()
                                .withProperty("name", "Ars")
                )
        );

        List<Team> result = repo.listTeams();
        assertThat(result, areTeams()
                .whichContainExhaustively(
                        aTeam().named("Ecom"),
                        aTeam().named("Ars")
                )
        );
    }

    @Test
    public void list_teams_retrieves_team_colour_from_db() {
        facewallTestDatabase.seedFixtures(newFixtures()
                .withTeams(
                        defaultTeamWithDefaultMembers()
                                .withProperty("colour", "blue"),
                        defaultTeamWithDefaultMembers()
                                .withProperty("colour", "red")
                )
        );

        List<Team> result = repo.listTeams();
        assertThat(result, areTeams()
                .whichContainExhaustively(
                        aTeam().withColour("blue"),
                        aTeam().withColour("red")
                )
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
}
