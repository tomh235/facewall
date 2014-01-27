package uk.co.o2.selenium.journeys;

import facewall.database.FacewallTestDatabase;
import org.junit.*;
import org.neo4j.graphdb.GraphDatabaseService;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.pages.HomePage;
import uk.co.o2.selenium.pages.RegisterPage;

import static facewall.database.FacewallTestDatabaseFactory.createFacewallTestDatabaseWrappingExistingDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.TeamDataFactory.defaultTeamWithDefaultMembers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.neo4j.rest.graphdb.GraphDatabaseFactory.databaseFor;

public class RegistrationTest extends SeleniumBase {

    private static GraphDatabaseService neoDb;
    private static FacewallTestDatabase facewallDb;
    private HomePage homePage;
    private RegisterPage registerPage;
    private final String NAME = "George Weasley";
    private final String EMPTY_NAME ="";
    private final String IMGURL = "http://theweasleys.com/george.jpg";
    private final String INVALID_IMGURL = "notaurl";
    private final String EMAIL = "george@theweasleys.com";
    private final String INVALID_EMAIL = "notanemail.com";
    private final String TEAM = "ecom";
    private final String SCRUM = "weasleys";
    private final String ROLE = "Developer";
    private final String LOCATION = "Bath Road";

    // TODO - embedded/single-use dBs for tests?
    @BeforeClass
    public static void beforeClass(){
        neoDb = databaseFor("http://localhost:7474/db/data/");
        facewallDb = createFacewallTestDatabaseWrappingExistingDatabase(neoDb);
        facewallDb.clear();
        facewallDb.initialise();
        facewallDb.seedFixtures(newFixtures().withTeams(
                defaultTeamWithDefaultMembers()
                        .withProperty("name", "ecom")
        ));
    }

    @Before
    public void beforeTest(){
        homePage = new HomePage();
        homePage.navigateToHomePage();
        //Initial landing on homepage

        registerPage = homePage.clickRegistrationTab();
        //Now on registration page
    }

    @AfterClass
    public static void afterTest(){
        facewallDb.clear();
        facewallDb.initialise();
    }

    @Test
    public void canRegisterUser() {
        //Fill in form
        registerPage.enterFieldInForm("name", NAME);
        registerPage.enterFieldInForm("imgURL", IMGURL);
        registerPage.enterFieldInForm("email", EMAIL);
        registerPage.selectDropdown("team", TEAM);
        registerPage.enterFieldInForm("scrum", SCRUM);
        registerPage.selectDropdown("role", ROLE);
        registerPage.selectDropdown("location", LOCATION);
        registerPage.clickSubmit();

        //Check all details submitted are returned
        assertThat(registerPage.getSummaryItem("name"), is(NAME));
        assertThat(registerPage.getSummaryItem("imgUrl"), is(IMGURL));
        assertThat(registerPage.getSummaryItem("email"), is(EMAIL));
        assertThat(registerPage.getSummaryItem("team"), is(TEAM));
        assertThat(registerPage.getSummaryItem("scrum"), is(SCRUM));
        assertThat(registerPage.getSummaryItem("role"), is(ROLE));
        assertThat(registerPage.getSummaryItem("location"), is(LOCATION));

        //Go to overview to check person is showing
        homePage.navigateToHomePage();
        assertThat(homePage.personExists(NAME, TEAM, IMGURL), is(true));
    }

    @Test
    public void formRejectsInvalidEmail() {
        //Fill in form
        registerPage.enterFieldInForm("name", NAME);
        registerPage.enterFieldInForm("imgURL", IMGURL);
        registerPage.enterFieldInForm("email", INVALID_EMAIL);
        registerPage.selectDropdown("team", TEAM);
        registerPage.enterFieldInForm("scrum", SCRUM);
        registerPage.selectDropdown("role", ROLE);
        registerPage.selectDropdown("location", LOCATION);
        registerPage.clickSubmit();

        assertThat(registerPage.onRegistrationPage(), is(true));

        //Go to overview to check person is not showing
        homePage.navigateToHomePage();
        assertThat(homePage.personExists(NAME, TEAM, INVALID_EMAIL), is(false));
    }

    @Test
    public void formRejectsInvalidImgUrl() {
        //Fill in form
        registerPage.enterFieldInForm("name", NAME);
        registerPage.enterFieldInForm("imgURL", INVALID_IMGURL);
        registerPage.enterFieldInForm("email", EMAIL);
        registerPage.selectDropdown("team", TEAM);
        registerPage.enterFieldInForm("scrum", SCRUM);
        registerPage.selectDropdown("role", ROLE);
        registerPage.selectDropdown("location", LOCATION);
        registerPage.clickSubmit();

        assertThat(registerPage.onRegistrationPage(), is(true));

        //Go to overview to check person is not showing
        homePage.navigateToHomePage();
        assertThat(homePage.personExists(NAME, TEAM, INVALID_IMGURL), is(false));
    }

    @Test
    public void formRejectsEmptyName() {
        //Fill in form
        registerPage.enterFieldInForm("name", EMPTY_NAME);
        registerPage.enterFieldInForm("imgURL", IMGURL);
        registerPage.enterFieldInForm("email", EMAIL);
        registerPage.selectDropdown("team", TEAM);
        registerPage.enterFieldInForm("scrum", SCRUM);
        registerPage.selectDropdown("role", ROLE);
        registerPage.selectDropdown("location", LOCATION);
        registerPage.clickSubmit();

        assertThat(registerPage.onRegistrationPage(), is(true));

        //Go to overview to check person is not showing
        homePage.navigateToHomePage();
        assertThat(homePage.personExists(NAME, TEAM, INVALID_IMGURL), is(false));
    }

}
