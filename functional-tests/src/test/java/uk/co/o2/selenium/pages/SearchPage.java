package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.selenium.common.WebBrowser;

public class SearchPage {

    public SinglePersonPage searchPerson(String personName) throws InterruptedException {
        WebBrowser.findElement(By.name("keywords")).sendKeys(personName);
        return new SinglePersonPage();
    }

    public SearchResultsPage searchTeam(String teamName) throws InterruptedException {
        WebBrowser.findElement(By.name("keywords")).sendKeys(teamName);
        return new SearchResultsPage();
    }
}
