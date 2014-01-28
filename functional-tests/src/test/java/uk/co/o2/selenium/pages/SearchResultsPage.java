package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.selenium.common.WebBrowser;

public class SearchResultsPage {

    public Boolean teamExists(String teamName) {
        if (WebBrowser.elementExists(By.xpath("//*[contains(text(), '" + teamName + "')]"))) {
            return true;
        }
        return false;
    }

    public Boolean hasNoResultsMessage() {
        if(WebBrowser.findElementWithFluidWait(By.className("no-results")).isDisplayed()) {
            return true;
        }
        return false;
    }
}
