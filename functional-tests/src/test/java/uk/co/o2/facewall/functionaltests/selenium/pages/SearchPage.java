package uk.co.o2.facewall.functionaltests.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.seleniumemulation.WaitForPageToLoad;
import uk.co.o2.facewall.functionaltests.selenium.common.WebBrowser;

public class SearchPage {

    public SinglePersonSearchResultsPage searchPerson(String personName) throws InterruptedException {
//        Thread.sleep(200L);
        new WaitForPageToLoad();
        WebBrowser.findElement(By.name("keywords")).sendKeys(personName);
        return new SinglePersonSearchResultsPage();
    }

    public SearchResultsPage searchTeam(String teamName) throws InterruptedException {
//        Thread.sleep(200L);
        new WaitForPageToLoad();
        WebBrowser.findElement(By.name("keywords")).sendKeys(teamName);
        return new SearchResultsPage();
    }
}
