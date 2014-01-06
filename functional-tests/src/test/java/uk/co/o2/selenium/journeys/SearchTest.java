package uk.co.o2.selenium.journeys;

import facewall.database.FacewallTestDatabase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import uk.co.o2.selenium.common.Configuration;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.common.WebBrowser;
import uk.co.o2.selenium.pages.HomePage;
import uk.co.o2.selenium.pages.SearchPage;
import uk.co.o2.selenium.pages.SinglePersonPage;

import static facewall.database.FacewallTestDatabaseFactory.createFacewallTestDatabaseWrappingExistingDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.PersonDataFactory.defaultPerson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.neo4j.rest.graphdb.GraphDatabaseFactory.databaseFor;

public class SearchTest extends SeleniumBase {

    static FacewallTestDatabase facewallDb;

    @BeforeClass
    public static void beforeClass() {
        GraphDatabaseService neoDb = databaseFor("http://localhost:7474/db/data/");
        facewallDb = createFacewallTestDatabaseWrappingExistingDatabase(neoDb);
        facewallDb.clear();
    }

    @AfterClass
    public static void afterClass() {
        facewallDb.clear();
    }

    //TODO: finish the test
    @Test
    public void searchForPerson() throws Exception {
        facewallDb.seedFixtures(newFixtures().withTeamlessPersons(defaultPerson().withProperty("name", "Fred Weasley")));
        WebBrowser.navigateTo(Configuration.baseUrl);
        HomePage homePage = new HomePage();
        SearchPage searchPage = homePage.clickSearchTab();
        SinglePersonPage singlePersonPage = searchPage.searchPerson("Fred Weasley");
        assertThat(singlePersonPage.getPersonName(), is("Fred Weasley"));
    }
}
