package uk.co.o2.selenium.journeys;

import facewall.database.FacewallTestDatabase;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import uk.co.o2.selenium.common.Configuration;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.common.WebBrowser;
import uk.co.o2.selenium.pages.HomePage;
import uk.co.o2.selenium.pages.RegisterPage;

import static facewall.database.FacewallTestDatabaseFactory.createFacewallTestDatabaseWrappingExistingDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.TeamDataFactory.defaultTeamWithDefaultMembers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.neo4j.rest.graphdb.GraphDatabaseFactory.databaseFor;

public class RegistrationTest extends SeleniumBase{

    @Test
    public void canRegisterUser() {
        GraphDatabaseService neoDb = databaseFor("http://localhost:7474/db/data/");
        FacewallTestDatabase facewallDb = createFacewallTestDatabaseWrappingExistingDatabase(neoDb);

        facewallDb.seedFixtures(newFixtures().withTeams(
                defaultTeamWithDefaultMembers()
                        .withProperty("name", "ecom")
        ));

        WebBrowser.navigateTo(Configuration.baseUrl);
        HomePage homePage = new HomePage();
        RegisterPage registerPage = homePage.clickRegistrationTab();
        registerPage.enterFieldInForm("name", "john smith");
        registerPage.enterFieldInForm("imgURL", "http://imgur.com/gallery/sXoVbIb");
        registerPage.enterFieldInForm("email", "john@smith.com");
        registerPage.enterFieldInForm("team", "ecom");
        registerPage.enterFieldInForm("scrum", "ecom");
        registerPage.selectDropdown("role", "Developer");
        registerPage.selectDropdown("location", "Bath Road");

        WebBrowser.navigateTo(Configuration.baseUrl);
        assertThat(homePage.personExists("john smith", "ecom","http://imgur.com/gallery/sXoVbIb"), is(true));
    }
}
