package uk.co.o2.selenium.journeys;

import org.junit.Ignore;
import uk.co.o2.selenium.common.Configuration;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.common.WebBrowser;
import uk.co.o2.selenium.pages.HomePage;
import uk.co.o2.selenium.pages.SearchPage;

public class SearchTest extends SeleniumBase{

    //TODO: finish the test
    @Ignore
    public void searchForUser() {
        WebBrowser.navigateTo(Configuration.baseUrl);
        HomePage homePage = new HomePage();
        SearchPage searchPage = homePage.clickSearchTab();
        searchPage.search("Fahran Wallace");

    }

}
