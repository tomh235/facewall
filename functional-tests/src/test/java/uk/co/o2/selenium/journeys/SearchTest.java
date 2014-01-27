package uk.co.o2.selenium.journeys;

import facewall.database.FacewallTestDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.pages.HomePage;
import uk.co.o2.selenium.pages.SearchPage;
import uk.co.o2.selenium.pages.SearchResultsPage;
import uk.co.o2.selenium.pages.SinglePersonPage;

import static facewall.database.FacewallTestDatabaseFactory.createFacewallTestDatabaseWrappingExistingDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.PersonDataFactory.defaultPerson;
import static facewall.database.fixture.TeamDataFactory.defaultTeamWithDefaultMembers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.neo4j.rest.graphdb.GraphDatabaseFactory.databaseFor;

public class SearchTest extends SeleniumBase {

    private static GraphDatabaseService neoDb;
    private static FacewallTestDatabase facewallDb;
    private HomePage homePage;
    private SearchPage searchPage;
    private SinglePersonPage singlePersonPage;
    private SearchResultsPage searchResultsPage;

    @BeforeClass
    public static void beforeClass() {
        neoDb = databaseFor("http://localhost:7474/db/data/");
        facewallDb = createFacewallTestDatabaseWrappingExistingDatabase(neoDb);
        facewallDb.clear();
        facewallDb.initialise();
    }

    @Before
    public void beforeTest() {
        homePage = new HomePage();
        homePage.navigateToHomePage();  //Initial landing on homepage
        searchPage = homePage.clickSearchTab(); //Now on search page
    }

    @After
    public void afterTest() throws InterruptedException {
        facewallDb.clear();
        facewallDb.initialise();
    }

    @Test
     public void searchForPerson() throws Exception {
        facewallDb.seedFixtures(newFixtures().withTeamlessPersons(defaultPerson().withProperty("name", "Fred Weasley")));
        singlePersonPage = searchPage.searchPerson("Fred Weasley");
        assertThat(singlePersonPage.getPersonName(), is("Fred Weasley"));
    }

    @Test
    public void searchForTeam() throws Exception {
        facewallDb.seedFixtures(newFixtures().withTeams(defaultTeamWithDefaultMembers().withProperty("name", "Ecom Ars")));
        searchResultsPage = searchPage.searchTeam("Ecom Ars");
        assertThat(searchResultsPage.teamExists("Ecom Ars"), is(true));
    }

    @Test
     public void noPersonSearchResults() throws Exception {
        facewallDb.seedFixtures(newFixtures().withTeamlessPersons(defaultPerson().withProperty("name", "Fred Weasley")));
        singlePersonPage = searchPage.searchPerson("Norman Cook");
        assertThat(singlePersonPage.personExists("Norman Cook"), is(false));
    }

    @Test
    public void noTeamSearchResults() throws Exception {
        facewallDb.seedFixtures(newFixtures().withTeams(defaultTeamWithDefaultMembers().withProperty("name", "Ecom Ars")));
        searchResultsPage = searchPage.searchTeam("Team unknown");
        assertThat(searchResultsPage.teamExists("Team unknown"), is(false));
    }

    @Test
    public void noTeamAndPersonSearchResults() throws Exception {
        searchResultsPage = searchPage.searchTeam("No results");
        assertThat(searchResultsPage.hasNoResultsMessage(), is(true));
    }

}
