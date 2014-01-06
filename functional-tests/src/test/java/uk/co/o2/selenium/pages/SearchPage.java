package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.selenium.common.WebBrowser;

public class SearchPage {

    public void search(String searchTerm) {
        WebBrowser.findElement(By.name("keywords")).sendKeys(searchTerm);
    }
}
