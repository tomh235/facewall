package uk.co.o2.selenium.journeys;

import org.junit.Test;
import uk.co.o2.selenium.common.Configuration;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.common.WebBrowser;
import uk.co.o2.selenium.pages.HomePage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HomePageTest extends SeleniumBase {

    @Test
    public void navbarIsPresentOnHomepage() {
        WebBrowser.navigateTo(Configuration.baseUrl);
        HomePage homepage = new HomePage();
        assertThat(homepage.navbarExists(), is(true));
    }

}
