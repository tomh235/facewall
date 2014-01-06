package uk.co.o2.selenium.journeys;

import org.junit.Ignore;
import uk.co.o2.selenium.common.Configuration;
import uk.co.o2.selenium.common.SeleniumBase;
import uk.co.o2.selenium.common.WebBrowser;
import uk.co.o2.selenium.pages.HomePage;
import uk.co.o2.selenium.pages.RegisterPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RegistrationTest extends SeleniumBase{

    //TODO: Not yet implemented
    @Ignore
    public void canRegisterUser() {
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
        registerPage.clickSubmit();
        assertThat(homePage.personExists("john smith", "ecom","http://imgur.com/gallery/sXoVbIb"), is(true));
    }
}
