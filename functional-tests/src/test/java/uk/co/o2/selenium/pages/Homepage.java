package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import uk.co.o2.selenium.common.WebBrowser;

public class Homepage {

    public String getTitle() {
        String x = WebBrowser.findElement(By.cssSelector("h1")).getText();
        return x;
    }

}
