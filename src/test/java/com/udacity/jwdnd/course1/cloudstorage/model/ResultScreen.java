package com.udacity.jwdnd.course1.cloudstorage.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResultScreen extends BaseScreen {

    public ResultScreen(WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText="here")
    private WebElement backToHomeLink;

    public boolean isResultScreenLoaded(){
        WebElement backToHomeLink = wait.until(webDriver -> webDriver.findElement(By.linkText("here")));
        return backToHomeLink.isDisplayed();
    }

    public void goToHomeScreen(){
        backToHomeLink.click();
    }
}
