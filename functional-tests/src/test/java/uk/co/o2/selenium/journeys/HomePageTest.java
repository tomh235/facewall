package uk.co.o2.selenium.journeys;

import org.junit.Test;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.pages.HomePage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class HomePageTest extends SeleniumBase {

    @Test
    public void isEverythingThere() {
        HomePage homePage = new HomePage();
        homePage.navigateToHomePage();
        assertThat(homePage.hasTitle(), is(true));
        assertThat(homePage.hasNavbar(), is(true));
        assertThat(homePage.hasPeople(), is (true));
        //assertThat(homePage.hasFooter(), is(true));
    }





}
