package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.selenium.common.WebBrowser;

public class SearchPage {

    public SinglePersonPage searchPerson(String searchTerm) throws InterruptedException {
        WebBrowser.findElement(By.name("keywords")).sendKeys(searchTerm);
        Thread.sleep(1000);
        return new SinglePersonPage();
    }
}
