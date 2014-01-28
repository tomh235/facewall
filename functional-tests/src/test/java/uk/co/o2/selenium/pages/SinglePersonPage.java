package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.selenium.common.WebBrowser;

public class SinglePersonPage {

    public String getPersonName() {
        return WebBrowser.findElementWithFluidWait(By.className("person-details-name")).getText();
    }

    public Boolean personExists(String personName) {
        return WebBrowser.elementExists(By.xpath("//*[contains(text(), '" + personName + "')]"));
    }
}
