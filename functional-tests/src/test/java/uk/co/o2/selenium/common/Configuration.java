package uk.co.o2.selenium.common;

public class Configuration {

    public static String testDriver = System.getProperty("qa.testdriver", "webdriver"); // "jsoup" or "webdriver"
    public static String browser = System.getProperty("qa.browser", "firefox");
    public static String browserVersion = System.getProperty("qa.browserVersion", "22");
    public static String server = System.getProperty("qa.hostName", "localhost");
    public static String serverPort = System.getProperty("qa.hostPort", "9000");

    public static String hostUrl = "http://" + server + ":" + serverPort;
    public static String baseUrl = hostUrl;

}
