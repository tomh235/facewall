package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.selenium.common.WebBrowser;

public class SearchResultsPage {

    public Boolean teamExists(String teamName) {
        if(WebBrowser.findElements(By.xpath("//*[contains(text(), '" + teamName + "')]")).size() > 0) {
            return true;
        }
        else return false;
    }

    public Boolean hasNoResultsMessage() {
        if(WebBrowser.findElement(By.className("no-results")).isDisplayed()) {
            return true;
        }
        else return false;
    }
}
