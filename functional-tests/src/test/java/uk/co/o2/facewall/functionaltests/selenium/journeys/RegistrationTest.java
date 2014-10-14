package uk.co.o2.facewall.functionaltests.selenium.journeys;

import uk.co.o2.facewall.databaseutils.FacewallTestDatabase;
import org.junit.*;
import org.neo4j.graphdb.GraphDatabaseService;
import uk.co.o2.facewall.functionaltests.selenium.common.SeleniumBase;
import uk.co.o2.facewall.functionaltests.selenium.pages.HomePage;
import uk.co.o2.facewall.functionaltests.selenium.pages.RegisterPage;

import static uk.co.o2.facewall.databaseutils.FacewallTestDatabaseFactory.createFacewallTestDatabaseWrappingExistingDatabase;
import static uk.co.o2.facewall.databaseutils.fixture.Fixtures.newFixtures;
import static uk.co.o2.facewall.databaseutils.fixture.TeamDataFactory.defaultTeamWithDefaultMembers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.neo4j.rest.graphdb.GraphDatabaseFactory.databaseFor;

public class RegistrationTest extends SeleniumBase {

    private static GraphDatabaseService neoDb;
    private static FacewallTestDatabase facewallDb;
    private HomePage homePage;
    private RegisterPage registerPage;
    private static final String NAME = "George Weasley";
    private static final String EMPTY_NAME ="";
    private static final String IMGURL = "http://theweasleys.com/george.jpg";
    private static final String INVALID_IMGURL = "notaurl";
    private static final String EMAIL = "george@theweasleys.com";
    private static final String INVALID_EMAIL = "notanemail.com";
    private static final String TEAM = "ecom";
    private static final String SCRUM = "weasleys";
    private static final String ROLE = "Developer";
    private static final String LOCATION = "Bath Road";

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
        facewallDb.clear();
        facewallDb.initialise();
        facewallDb.seedFixtures(newFixtures().withTeams(
                defaultTeamWithDefaultMembers()
                        .withProperty("name", "ecom")
        ));
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
    public void form_has_input_for_teams_when_no_team_in_db() {
        facewallDb.clear();
        facewallDb.initialise();
        homePage.navigateToHomePage();
        homePage.clickRegistrationTab();
        assertThat(registerPage.getInputTag("team"), is("input"));
    }

    @Test
    public void form_has_dropdown_for_teams_when_teams_in_db() {
        assertThat(registerPage.getInputTag("team"), is("select"));
    }

    @Test
    public void can_complete_register_user_journey() {
        //Fill in form
        registerPage.enterFieldInForm("name", NAME);
        registerPage.enterFieldInForm("imgUrl", IMGURL);
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
        assertThat(registerPage.getSummaryItem("role"), is(ROLE));

        //Go to overview to check person is showing
        homePage.navigateToHomePage();
        assertThat(homePage.personExists(NAME, TEAM, IMGURL), is(true));
    }

    @Test
    public void sign_up_summary_displays_inputted_data() {
        //Fill in form
        registerPage.enterFieldInForm("name", NAME);
        registerPage.enterFieldInForm("imgUrl", IMGURL);
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
        assertThat(registerPage.getSummaryItem("role"), is(ROLE));

        //Go to overview to check person is showing
        homePage.navigateToHomePage();
        assertThat(homePage.personExists(NAME, TEAM, IMGURL), is(true));
    }

    @Test
    public void form_rejects_invalid_email_field() {
        //Fill in form
        registerPage.enterFieldInForm("name", NAME);
        registerPage.enterFieldInForm("imgUrl", IMGURL);
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
    public void form_rejects_invalid_url_field() {
        //Fill in form
        registerPage.enterFieldInForm("name", NAME);
        registerPage.enterFieldInForm("imgUrl", INVALID_IMGURL);
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
    public void form_rejects_empty_name_field() {
        //Fill in form
        registerPage.enterFieldInForm("name", EMPTY_NAME);
        registerPage.enterFieldInForm("imgUrl", IMGURL);
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
