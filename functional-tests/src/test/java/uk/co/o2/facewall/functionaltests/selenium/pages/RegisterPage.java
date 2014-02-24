package uk.co.o2.facewall.functionaltests.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import uk.co.o2.facewall.functionaltests.selenium.common.WebBrowser;

public class RegisterPage {

    public void enterFieldInForm(String name, String value) {
        WebBrowser.findElement(By.name(name)).sendKeys(value);
    }

    public void selectDropdown(String name, String value) {
        Select select = new Select(WebBrowser.findElement(By.name(name)));
        select.selectByVisibleText(value);
    }

    public void clickSubmit() {
        WebBrowser.findElement(By.id("submit")).click();
    }

    public String getSummaryItem(String className) {
        return WebBrowser.findElement(By.className(className)).getText();
    }

    public boolean onRegistrationPage() {
        return WebBrowser.getUrl().matches(".*\\/signup");
    }

    public String getInputTag(String fieldName) {
        WebElement inputField = WebBrowser.findElement(By.xpath("//*[@name='" + fieldName + "']"));
        return inputField.getTagName();
    }
}
