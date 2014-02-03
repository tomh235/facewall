package uk.co.o2.selenium.common;

import com.google.common.base.Function;
import com.thoughtworks.selenium.DefaultSelenium;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

public class WebBrowser {

    private static WebDriver driver = null;
    public static DefaultSelenium selenium = null;
    public static Map<String, String> cookies = new HashMap<String, String>();
    public static boolean javascriptEnabled = true;
    public static String browserName = "";
    public static boolean mobileVersion = false;

    public WebBrowser() {
    }

    public static WebDriver start(String browser, String browserVersion) {

        DesiredCapabilities capabillities = null;

        System.out.println("Setting up WebDriver using: " + browser + " with version " + browserVersion);
        driver = null;
        browserName = browser;

        // firefox local
        if (browser.equalsIgnoreCase("firefox")) {
            capabillities = DesiredCapabilities.firefox();
            capabillities.setCapability("version", browserVersion);
            capabillities.setCapability("platform", Platform.LINUX);
            capabillities.setJavascriptEnabled(true);
            driver = new FirefoxDriver(capabillities);
        }
        // Html local
        if (browser.equalsIgnoreCase("html")) {
            capabillities = DesiredCapabilities.htmlUnit();
            /*capabillities.setCapability("version", browserVersion);
            capabillities.setCapability("platform", Platform.LINUX);*/
            capabillities.setJavascriptEnabled(true);
            driver = new HtmlUnitDriver(capabillities);
        }
        // firefox remote
        else if (browser.equalsIgnoreCase("firefoxRemote")) {
            capabillities = DesiredCapabilities.firefox();
            capabillities.setCapability("version", browserVersion);
            capabillities.setCapability("platform", Platform.LINUX);
            capabillities.setJavascriptEnabled(true);
        }
        //ie
        else if (browser.equalsIgnoreCase("iexplore")) {
            capabillities = DesiredCapabilities.internetExplorer();
            capabillities.setCapability("version", browserVersion);
            if (browserVersion != null && browserVersion.startsWith("9"))
                capabillities.setCapability("platform", Platform.VISTA);
            else
                capabillities.setCapability("platform", Platform.XP);
            capabillities.setJavascriptEnabled(true);
            capabillities.setCapability("selenium-version", "2.18.0");
        }
        //chrome
        else if (browser.equalsIgnoreCase("chrome")) {
            capabillities = DesiredCapabilities.chrome();
            capabillities.setCapability("platform", Platform.XP);
            capabillities.setJavascriptEnabled(true);
        }
        //opera
        else if (browser.equalsIgnoreCase("opera")) {
            capabillities = DesiredCapabilities.opera();
            capabillities.setCapability("platform", Platform.LINUX);
            capabillities.setJavascriptEnabled(true);
        }
        //safari     browserVersion=5.0
        else if (browser.equalsIgnoreCase("safari")) {

            capabillities = DesiredCapabilities.safari();
            capabillities.setCapability("platform", "Windows 2008");
            capabillities.setCapability("version", browserVersion);
            capabillities.setJavascriptEnabled(true);
        }
        //firefox with js off
        else if (browser.equalsIgnoreCase("jsDisabledFirefox")) {

            final FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("javascript.enabled", false);
            driver = new FirefoxDriver(profile);
            javascriptEnabled = false;
        }
        // mobile screen size
        else if (browser.contains("iphone-FF") || browser.contains("ipad-FF")) {
            capabillities = DesiredCapabilities.firefox();
            capabillities.setCapability("platform", Platform.LINUX);
            capabillities.setJavascriptEnabled(true);
            FirefoxProfile profile = new FirefoxProfile();
            if (browser.contains("iphone")) {
                profile.setPreference("general.useragent.override", "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16");
            } else {
                profile.setPreference("general.useragent.override", "Mozilla/5.0    (iPad; U; CPU iPhone OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B314 Safari/531.21.10");
            }
            capabillities.setCapability("firefox_profile", profile);

            // mobile screen size
            if (browser.equalsIgnoreCase("iphoneCI") || browser.equalsIgnoreCase("ipadCI")) {
                driver = new FirefoxDriver(capabillities);
            }
        }
        //iphone with default browser (browserversion - 4.3 or 5)
        else if (browser.equalsIgnoreCase("iphone")) {
            mobileVersion = true;
            capabillities = DesiredCapabilities.iphone();
            capabillities.setCapability("version", browserVersion);
            capabillities.setCapability("platform", Platform.MAC);
            capabillities.setJavascriptEnabled(true);
        }
        //android with default browser (browserversion - 4.0)
        else if (browser.equalsIgnoreCase("android")) {
            mobileVersion = true;
            capabillities = DesiredCapabilities.android();
            capabillities.setCapability("version", browserVersion);
            capabillities.setCapability("platform", Platform.LINUX);
            capabillities.setJavascriptEnabled(true);
        }
        //ipad  with default browser (browserversion - 4.3 or 5.0)
        else if (browser.equalsIgnoreCase("ipad")) {
            capabillities = DesiredCapabilities.ipad();
            capabillities.setCapability("version", browserVersion);
            capabillities.setCapability("platform", Platform.MAC);
            capabillities.setJavascriptEnabled(true);
        }

        // if remote driver
        if (driver == null) {
            URL rwds = null;

            capabillities = DesiredCapabilities.safari();
            capabillities.setCapability("version", "5");
            capabillities.setCapability("platform", "Windows 7");
            capabillities.setCapability("record-video", "true");
            try {
                driver = new RemoteWebDriver(
                        new URL("http://rormrod:5f4e65d2-f218-4637-8b7d-78c6514d1d08@ondemand.saucelabs.com:80/wd/hub"),
                        capabillities);
            } catch (MalformedURLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return driver;
    }

    public static void closeBrowser() {
        driver.close();
    }

    public static void deleteCookieNamed(String named) {
        driver.manage().deleteCookieNamed(named);
    }

    public static Set<Cookie> getCookies() {
        return driver.manage().getCookies();
    }

    public static JavascriptExecutor getJavascriptExecutor(){
        return (JavascriptExecutor)driver;
    }

    public static boolean deleteAllCookies() {

        if (driver == null) {
            // work-around: seems to be called by Idea out-of-order
            return true;
        }

        // maintain list of used cookie for reporting
        try {
            for (final Cookie cookie : driver.manage().getCookies()) {
                cookies.put(cookie.getName(), cookie.getName());
            }
        } catch (final Exception e) {
//            Reporter.log("Could not get cookies " + e);
            System.out.println("Could not get cookies " + e);
        }

        // deletecookies
        try {
            driver.manage().deleteAllCookies();
        } catch (final Exception e) {
//            Reporter.log("Could not delete cookies " + e);
            System.out.println("Could not delete cookies " + e);
        }
        return false;
    }

    public static void navigateTo(final String url) {
        driver.get(url);
    }

    public static WebElement findElement(final By by) {
        return driver.findElement(by);
    }

    public static WebElement findElementWithFluidWait(final By by) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(6000, MILLISECONDS)
                .pollingEvery(250, MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(by);
            }
        });
        return  foo;
    };

    public static boolean elementExists(final By by) {
        try {
            findElementWithFluidWait(by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void switchTo(String frameName) {
        driver.switchTo().frame(frameName);
    }

    public static void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public static void switchTo(Integer index) {
        driver.switchTo().frame(0);
    }

    public static String getUrl() {
        return driver.getCurrentUrl();
    }

    public static String getTitle() {
        return driver.getTitle();
    }

    public static void refresh(){
        driver.navigate().refresh();
    }

    public static String getPageSource() {
        return driver.getPageSource();
    }

    public static List<WebElement> findElements(final By by) {
        return driver.findElements(by);
    }

    public static void browserBack() {
        driver.navigate().back();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    public static void takeScreenShot(String pictureName){
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(scrFile, new File("/home/ee/Pictures/" + pictureName + ".png"));
        } catch (IOException e) {
            System.out.println("!!!!!!!!!!!!!!!!!! TRYING TO SAVE SCREENSHOT FAILED !!!!!!!!!!!!!!!!!!!!!!!");
        }
    }

    public static WebDriverWait getWebdriverWaitObject(long timeout){
        return new WebDriverWait(driver, timeout);
    }

    public static int getWindowHandleCount(){
        return driver.getWindowHandles().size();
    }
}
