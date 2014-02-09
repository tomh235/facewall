package uk.co.o2.facewall.functionaltests.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.facewall.functionaltests.selenium.common.WebBrowser;

public class SinglePersonSearchResultsPage {

    public String getPersonName() {
        return WebBrowser.findElementWithFluidWait(By.className("person-details-name")).getText();
    }

    public Boolean personExists(String personName) {
        return WebBrowser.elementExists(By.xpath("//*[contains(text(), '" + personName + "')]"));
    }

    public Boolean imageExists() {
        return WebBrowser.elementExists(By.className("person-details-img"));
    }

    public Boolean hasEmail(String personEmail){
        return WebBrowser.elementExists(By.xpath("//*[contains(text(), '" + personEmail + "')]"));
    }

    public Boolean hasRole(String personRole){
        return WebBrowser.elementExists(By.xpath("//*[contains(text(), '" + personRole + "')]"));
    }
}
