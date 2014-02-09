package uk.co.o2.facewall.functionaltests.selenium.common;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class SeleniumBase {

    @BeforeClass
    public static void beforeTestClassAction() {
        if (Configuration.testDriver.equals("webdriver")) {
            WebBrowser.start(Configuration.browser, Configuration.browserVersion);
        }
    }

    @Before
    public void beforeTestAction() {
        TestContext.cleanContext();
        if (Configuration.testDriver.equals("webdriver")) {
            WebBrowser.deleteAllCookies();
        }
    }

    @AfterClass
    public static void afterTestClassAction() {
        if (Configuration.testDriver.equals("webdriver")) {
            WebBrowser.closeBrowser();
        }
    }

}
