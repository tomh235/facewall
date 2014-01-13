package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.selenium.common.WebBrowser;

public class SinglePersonPage {

    public String getPersonName() {
        return WebBrowser.findElement(By.className("personName")).getText();
    }

    public Boolean hasNoResults() {
        if(WebBrowser.findElement(By.className("no-results")).isDisplayed()) {
            return true;
        }
        else return false;
    }
}
