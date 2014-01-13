package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.selenium.common.WebBrowser;

public class SinglePersonPage {

    public String getPersonName() {
        return WebBrowser.findElement(By.className("personName")).getText();
    }

    public Boolean hasNoResultsMessage() {
        if(WebBrowser.findElement(By.className("no-results-person")).isDisplayed()) {
            return true;
        }
        else return false;
    }

    public Boolean personExists(String personName) {
        if(WebBrowser.findElements(By.xpath("//*[contains(text(), '" + personName + "')]")).size() > 0) {
            return true;
        }
        else return false;
    }
}
