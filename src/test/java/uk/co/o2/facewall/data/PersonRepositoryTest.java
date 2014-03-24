package uk.co.o2.facewall.data;

import uk.co.o2.facewall.domain.Person;
import uk.co.o2.facewall.domain.Query;
import uk.co.o2.facewall.databaseutils.FacewallTestDatabase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static uk.co.o2.facewall.data.DataModule.createDataModule;
import static uk.co.o2.facewall.domain.PersonMatcher.aPerson;
import static uk.co.o2.facewall.domain.PersonsMatcher.arePersons;
import static uk.co.o2.facewall.domain.TeamMatcher.aTeam;
import static uk.co.o2.facewall.domain.datatype.QueryString.newQueryString;
import static uk.co.o2.facewall.databaseutils.FacewallTestDatabaseFactory.createImpermanentFacewallTestDatabase;
import static uk.co.o2.facewall.databaseutils.fixture.Fixtures.newFixtures;
import static uk.co.o2.facewall.databaseutils.fixture.FixturesFactory.defaultFixtures;
import static uk.co.o2.facewall.databaseutils.fixture.PersonDataFactory.defaultPerson;
import static uk.co.o2.facewall.databaseutils.fixture.PersonDataFactory.defaultPersons;
import static uk.co.o2.facewall.databaseutils.fixture.TeamDataFactory.defaultTeam;
import static uk.co.o2.facewall.databaseutils.fixture.TeamDataFactory.defaultTeamWithDefaultMembers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersonRepositoryTest {

    private FacewallTestDatabase facewallTestDatabase;
    private PersonRepository repository;
    private List<Person> result;

    @Before
    public void setUp() throws Exception {
        facewallTestDatabase = createImpermanentFacewallTestDatabase();
        repository = createDataModule(facewallTestDatabase.createQueryEngine(), facewallTestDatabase).personRepository;
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

        result = repository.listPersons();
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

        result = repository.listPersons();
        assertThat(result, arePersons()
                .whichContainExhaustively(
                        aPerson().withPicture("Bill.img"),
                        aPerson().withPicture("Ben.img")
                )
        );
    }

    @Test
    public void list_persons_retrieves_persons_with_emails_from_db() {
        facewallTestDatabase.seedFixtures(newFixtures()
                .withTeamlessPersons(defaultPerson()
                        .withProperty("id", "email1@testemail.com")
                ).withTeams(defaultTeam()
                        .withMembers(defaultPerson()
                                .withProperty("id", "email2@testemail.com")
                        )
                )
        );

        result = repository.listPersons();
        assertThat(result, arePersons()
                .whichContainExhaustively(
                        aPerson().withEmail("email1@testemail.com"),
                        aPerson().withEmail("email2@testemail.com")
                )
        );
    }

    @Test
    public void list_persons_retrieves_persons_with_roles_from_db() {
        facewallTestDatabase.seedFixtures(newFixtures()
                .withTeamlessPersons(defaultPerson()
                        .withProperty("role", "candlestickmaker")
                ).withTeams(defaultTeam()
                        .withMembers(defaultPerson()
                                .withProperty("role", "butcher")
                        )
                )
        );

        result = repository.listPersons();
        assertThat(result, arePersons()
                .whichContainExhaustively(
                        aPerson().withRole("candlestickmaker"),
                        aPerson().withRole("butcher")
                )
        );
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

        result = repository.listPersons();

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

        result = repository.listPersons();

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
                                                .withProperty("id", "email1@testemail.com")
                                                .withProperty("role", "a poor tea"),
                                        defaultPerson()
                                                .withProperty("name", "Yorkshire Tea")
                                                .withProperty("picture", "The North.png")
                                                .withProperty("id", "email2@testemail.com")
                                                .withProperty("role", "a good tea")
                                ),
                        defaultTeam()
                                .withMembers(
                                        defaultPerson()
                                                .withProperty("name", "Gold blend")
                                                .withProperty("picture", "nescafe-gold-blend.img")
                                                .withProperty("id", "email3@testemail.com")
                                                .withProperty("role", "awakeness")
                                )
                )
        );

        result = repository.listPersons();

        assertThat(result, arePersons()
                .whichContains(aPerson()
                        .named("Earl Grey")
                        .withPicture("whittard-earl-grey.png")
                        .withEmail("email1@testemail.com")
                        .withRole("a poor tea"))
                .whichContains(aPerson()
                        .named("Yorkshire Tea")
                        .withPicture("The North.png")
                        .withEmail("email2@testemail.com")
                        .withRole("a good tea"))

                .whichContains(aPerson()
                        .named("Gold blend")
                        .withPicture("nescafe-gold-blend.img")
                        .withEmail("email3@testemail.com")
                        .withRole("awakeness"))
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

        result = repository.listPersons();

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
                                .withProperty("id", "email1@testemail.com"),
                        defaultPerson()
                                .withProperty("name", "delivery manager")
                                .withProperty("picture", "important-looking-person2.img")
                                .withProperty("id", "email2@testemail.com")
                )
        );

        result = repository.listPersons();

        assertThat(result, arePersons()
                .whichContains(aPerson()
                        .named("development manager")
                        .withPicture("important-looking-person1.img")
                        .withEmail("email1@testemail.com")
                        .whoIsNotInATeam())
                .whichContains(aPerson()
                        .named("delivery manager")
                        .withPicture("important-looking-person2.img")
                        .withEmail("email2@testemail.com")
                        .whoIsNotInATeam())
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

        result = repository.listPersons();

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

        result = repository.queryPersons(query);
        assertThat(result, arePersons().whichContainExhaustively(
                aPerson().named("rickety doodle"),
                aPerson().named("poodle do")
        ));
    }
}
