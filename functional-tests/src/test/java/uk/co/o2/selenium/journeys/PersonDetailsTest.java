package uk.co.o2.selenium.journeys;

import facewall.database.FacewallTestDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.pages.HomePage;
import uk.co.o2.selenium.pages.PersonDetailsPage;

import static facewall.database.FacewallTestDatabaseFactory.createFacewallTestDatabaseWrappingExistingDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.PersonDataFactory.defaultPerson;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.neo4j.rest.graphdb.GraphDatabaseFactory.databaseFor;

public class PersonDetailsTest extends SeleniumBase {

    private static final String PERSON_NAME = "Fred Weasley";
    private static final String EMAIL = "fred.weasley@murderedinhogwarts.net";
    private static final String ROLE = "dead";
    private static GraphDatabaseService neoDb;
    private static FacewallTestDatabase facewallDb;
    private HomePage homePage;
    private PersonDetailsPage personDetailsPage;

    @BeforeClass
    public static void beforeClass() {
        neoDb = databaseFor("http://localhost:7474/db/data/");
        facewallDb = createFacewallTestDatabaseWrappingExistingDatabase(neoDb);
        facewallDb.clear();
        facewallDb.initialise();
    }

    @Before
    public void beforeTest() {
        facewallDb.clear();
        facewallDb.initialise();
        facewallDb.seedFixtures(newFixtures().withTeamlessPersons(defaultPerson()
                .withProperty("name", PERSON_NAME)
                .withProperty("email", EMAIL)
                .withProperty("role", ROLE)
        ));
        homePage = new HomePage();
        homePage.navigateToHomePage();  // initial landing on homepage
        personDetailsPage = homePage.clickPerson(PERSON_NAME); //click through to person details page
    }

    @After
    public void afterTest() throws InterruptedException {
        facewallDb.clear();
        facewallDb.initialise();
    }

    @Test
    public void person_has_a_name() {
        assertThat(personDetailsPage.getPersonName(), is(PERSON_NAME));
    }

    @Test
    public void person_has_an_img_tag() {
            assertThat(personDetailsPage.imageExists(), is(true));
    }

    @Test
    public void person_has_an_email() {
        assertThat(personDetailsPage.hasEmail(EMAIL), is(true));
    }

    @Test
    public void person_has_a_role() {
        assertThat(personDetailsPage.hasRole(ROLE), is(true));
    }

}
