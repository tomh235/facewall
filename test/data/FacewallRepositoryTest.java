package data;

import data.datatype.PersonId;
import domain.Person;
import domain.Query;
import domain.Team;
import facewall.database.FacewallTestDatabase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static data.DataModule.createRepository;
import static data.datatype.PersonId.newPersonId;
import static domain.PersonMatcher.aPerson;
import static domain.PersonsMatcher.arePersons;
import static domain.TeamMatcher.aTeam;
import static domain.TeamsMatcher.areTeams;
import static facewall.database.FacewallTestDatabaseFactory.createImpermanentFacewallTestDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.PersonDataFactory.defaultPerson;
import static facewall.database.fixture.TeamDataFactory.defaultTeam;
import static facewall.database.fixture.TeamDataFactory.defaultTeamWithDefaultMembers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class FacewallRepositoryTest {
    private static final Query someQuery = mock(Query.class);
    private static final PersonId somePersonId = newPersonId("some-id");

    private FacewallTestDatabase facewallTestDatabase;
    private Repository repository;

    @Before
    public void setUp() throws Exception {
        facewallTestDatabase = createImpermanentFacewallTestDatabase();
        repository = createRepository(facewallTestDatabase.createQueryEngine());
    }

    @Test
    public void list_persons_retrieves_person_names_from_db() {
        facewallTestDatabase.seedFixtures(newFixtures()
                .withTeamlessPersons(defaultPerson()
                        .withProperty("name", "Bill")
                ).withTeams(defaultTeam()
                        .withMembers(defaultPerson()
                                .withProperty("name", "Ben")
                        )
                )
        );

        List<Person> result = repository.listPersons();
        assertThat(result, arePersons()
                .whichContainExhaustively(
                        aPerson().named("Bill"),
                        aPerson().named("Ben")
                )
        );
    }

    @Test
    public void list_persons_retrieves_person_pictures_from_db() {
        facewallTestDatabase.seedFixtures(newFixtures()
                .withTeamlessPersons(defaultPerson()
                        .withProperty("picture", "Bill.img")
                ).withTeams(defaultTeam()
                        .withMembers(defaultPerson()
                                .withProperty("picture", "Ben.img")
                        )
                )
        );

        List<Person> result = repository.listPersons();
        assertThat(result, arePersons()
                .whichContainExhaustively(
                        aPerson().withPicture("Bill.img"),
                        aPerson().withPicture("Ben.img")
                )
        );
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

        List<Team> result = repository.listTeams();
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

        List<Team> result = repository.listTeams();
        assertThat(result, areTeams()
                .whichContainExhaustively(
                        aTeam().withColour("blue"),
                        aTeam().withColour("red")
                )
        );
    }
}
