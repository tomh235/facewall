package uk.co.o2.selenium.journeys;

import facewall.database.FacewallTestDatabase;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
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
    private final String name = "George Weasley";
    private final String imgUrl = "http://theweasleys.com/george.jpg";
    private final String email = "george@theweasleys.com";
    private final String team = "ecom";
    private final String scrum = "weasleys";
    private final String role = "Developer";
    private final String location = "Bath Road";

    // TODO - embedded/single-use dBs for tests?
    @BeforeClass
    public static void beforeClass(){
        neoDb = databaseFor("http://localhost:7474/db/data/");
        facewallDb = createFacewallTestDatabaseWrappingExistingDatabase(neoDb);
        facewallDb.clear();
        facewallDb.initialise();
    }

    @After
    public void afterTest(){
        facewallDb.clear();
        facewallDb.initialise();
    }

    @Test
    public void canRegisterUser() {
        facewallDb.seedFixtures(newFixtures().withTeams(
                defaultTeamWithDefaultMembers()
                        .withProperty("name", "ecom")
        ));

        homePage = new HomePage();
        homePage.navigateToHomePage();
        //Initial landing on homepage

        registerPage = homePage.clickRegistrationTab();
        //Now on registration page

        //Fill in form
        registerPage.enterFieldInForm("name", name);
        registerPage.enterFieldInForm("imgURL", imgUrl);
        registerPage.enterFieldInForm("email", email);
        registerPage.enterFieldInForm("team", team);
        registerPage.enterFieldInForm("scrum", scrum);
        registerPage.selectDropdown("role", role);
        registerPage.selectDropdown("location", location);
        registerPage.clickSubmit();

        //Check all details submitted are returned
        assertThat(registerPage.getSummaryItem("name"), is(name));
        assertThat(registerPage.getSummaryItem("imgUrl"), is(imgUrl));
        assertThat(registerPage.getSummaryItem("email"), is(email));
        assertThat(registerPage.getSummaryItem("team"), is(team));
        assertThat(registerPage.getSummaryItem("scrum"), is(scrum));
        assertThat(registerPage.getSummaryItem("role"), is(role));
        assertThat(registerPage.getSummaryItem("location"), is(location));

        //Go to overview to check person is showing
        homePage.navigateToHomePage();
        assertThat(homePage.personExists(name, team, imgUrl), is(true));
    }
}
