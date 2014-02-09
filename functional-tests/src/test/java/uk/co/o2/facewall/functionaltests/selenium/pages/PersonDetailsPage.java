package uk.co.o2.facewall.functionaltests.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.facewall.functionaltests.selenium.common.WebBrowser;

public class PersonDetailsPage {

    public String getPersonName() {
        return WebBrowser.findElementWithFluidWait(By.className("personName")).getText();
    }

    public Boolean imageExists() {
        return WebBrowser.elementExists(By.className("single-person-pic"));
    }

    public Boolean hasEmail(String personEmail){
        return WebBrowser.elementExists(By.xpath("//*[contains(text(), '" + personEmail + "')]"));
    }

    public Boolean hasRole(String personRole){
        return WebBrowser.elementExists(By.xpath("//*[contains(text(), '" + personRole + "')]"));
    }
}
