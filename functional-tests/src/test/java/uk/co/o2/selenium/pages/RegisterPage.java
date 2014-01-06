package uk.co.o2.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import uk.co.o2.selenium.common.WebBrowser;

public class RegisterPage {

    public void enterFieldInForm(String name, String value) {
        WebBrowser.findElement(By.name(name)).sendKeys(value);
    }

    public void selectDropdown(String name, String value){
        Select select = new Select(WebBrowser.findElement(By.name(name)));
        select.selectByVisibleText(value);
    }

    public void clickSubmit() {
        WebBrowser.findElement(By.id("submit")).click();
    }
}
