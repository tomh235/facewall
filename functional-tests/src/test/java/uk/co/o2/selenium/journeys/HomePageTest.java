package uk.co.o2.selenium.journeys;

import facewall.database.FacewallTestDatabase;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.pages.HomePage;

import static facewall.database.FacewallTestDatabaseFactory.createFacewallTestDatabaseWrappingExistingDatabase;
import static facewall.database.fixture.Fixtures.newFixtures;
import static facewall.database.fixture.TeamDataFactory.defaultTeamWithDefaultMembers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.neo4j.rest.graphdb.GraphDatabaseFactory.databaseFor;

public class HomePageTest extends SeleniumBase {

    private static GraphDatabaseService neoDb;
    private static FacewallTestDatabase facewallDb;
    private HomePage homePage;

    @BeforeClass
    public static void beforeClass() {
        neoDb = databaseFor("http://localhost:7474/db/data/");
        facewallDb = createFacewallTestDatabaseWrappingExistingDatabase(neoDb);
        facewallDb.clear();
        facewallDb.initialise();
        facewallDb.seedFixtures(newFixtures().withTeams(defaultTeamWithDefaultMembers().withProperty("name", "Ecom Ars")));
    }

    @Before
    public void beforeTest() {
        homePage = new HomePage();
        homePage.navigateToHomePage();
    }

    @AfterClass
    public static void afterTest(){
        facewallDb.clear();
        facewallDb.initialise();
    }

    @Test
    public void pageElementsExist() {
        assertThat(homePage.hasTitle(), is(true));
        assertThat(homePage.hasNavbar(), is(true));
        assertThat(homePage.hasPeople(), is(true));
        assertThat(homePage.peopleHaveImages(), is(true));
        //assertThat(homePage.hasFooter(), is(true));
    }
}
