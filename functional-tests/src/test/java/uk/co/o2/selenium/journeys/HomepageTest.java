package uk.co.o2.selenium.journeys;

import org.junit.Test;
import uk.co.o2.selenium.common.Configuration;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.common.WebBrowser;
import uk.co.o2.selenium.pages.Homepage;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

//@RunWith(JUnitRunner.class)
public class HomepageTest extends SeleniumBase {

    @Test
    public void canDisplayAllDataOnHomepage() {
        WebBrowser.navigateTo(Configuration.url);
        Homepage homepage = new Homepage();
        assertThat(homepage.getTitle(), is("Facewall"));
    }
}
