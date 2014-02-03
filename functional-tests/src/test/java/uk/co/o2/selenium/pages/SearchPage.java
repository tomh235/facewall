package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.selenium.common.WebBrowser;

public class SearchPage {

    public SinglePersonSearchResultsPage searchPerson(String personName) throws InterruptedException {
        WebBrowser.findElement(By.name("keywords")).sendKeys(personName);
        return new SinglePersonSearchResultsPage();
    }

    public SearchResultsPage searchTeam(String teamName) throws InterruptedException {
        WebBrowser.findElement(By.name("keywords")).sendKeys(teamName);
        return new SearchResultsPage();
    }
}
