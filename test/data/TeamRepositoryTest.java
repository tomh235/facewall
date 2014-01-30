package data;

import domain.Person;
import domain.Query;
import domain.Team;
import facewall.database.FacewallTestDatabase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static data.DataModule.createDataModule;
import static data.datatype.PersonId.newPersonId;
import static data.datatype.TeamId.newTeamId;
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
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static util.IterableMatchers.contains;

public class TeamRepositoryTest {

    private FacewallTestDatabase facewallTestDatabase;
    private DataModule dataModule;
    private TeamRepository teamRepository;
    private PersonRepository personRepository;
    private List<Team> result;

    @Before
    public void setUp() throws Exception {
        facewallTestDatabase = createImpermanentFacewallTestDatabase();
        dataModule = createDataModule(facewallTestDatabase.createQueryEngine(), facewallTestDatabase);
        teamRepository = dataModule.teamRepository;
        personRepository = dataModule.personRepository;
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

        result = teamRepository.listTeams();
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

        result = teamRepository.listTeams();
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

        result = teamRepository.listTeams();

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

        result = teamRepository.listTeams();

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

        result = teamRepository.listTeams();

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

        result = teamRepository.listTeams();

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

        result = teamRepository.queryTeams(query);
        assertThat(result, areTeams().whichContainExhaustively(
                aTeam().named("should match this team"),
                aTeam().named("should match this team as well")
        ));
    }

    @Test
    public void findTeamById_with_member_added() {
        facewallTestDatabase.seedFixtures(defaultFixtures()
                .withTeams(
                        defaultTeamWithDefaultMembers()
                                .withProperty("id", "ferrari-id")
                                .withProperty("name", "ferrari"))
                .withTeamlessPersons(
                        defaultPerson()
                                .withProperty("id", "kimmi-id")
                                .withProperty("name", "kimmi")
                )
        );

        Team team = teamRepository.findTeamById(newTeamId("ferrari-id"));
        Person member = personRepository.findPersonById(newPersonId("kimmi-id"));

        team.addMember(member);

        Team result = teamRepository.findTeamById(newTeamId("ferrari-id"));
        assertThat(result, is(aTeam()
                .named("ferrari")
                .whereMembers(contains(
                        aPerson().named("kimmi")
                ))
        ));
    }
}
