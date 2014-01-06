package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import uk.co.o2.selenium.common.WebBrowser;

import java.util.List;

public class HomePage {

    public boolean navbarExists() {
       return WebBrowser.findElements(By.className("nav")).size() == 1;
    }

    public RegisterPage clickRegistrationTab() {
        WebBrowser.findElement(By.id("register")).click();
        return new RegisterPage();
    }

    public SearchPage clickSearchTab() {
        WebBrowser.findElement(By.id("search")).click();
        return new SearchPage();
    }

    public boolean personExists(String name, String team, String imgUrl) {
        List<WebElement> elements = WebBrowser.findElements(By.className("entry"));

        for (WebElement element: elements) {
            String currentName = element.findElement(By.className("entryname")).getText();
            String currentTeam = element.findElement(By.className("teamname")).getText();
            String currentImgUrl  = element.findElement(By.cssSelector("img")).getAttribute("src");
            if(currentName.equals(name) && currentTeam.equals(team) && currentImgUrl.equals(imgUrl)) {
                return true;
            }
        }
        return false;
    }
}
