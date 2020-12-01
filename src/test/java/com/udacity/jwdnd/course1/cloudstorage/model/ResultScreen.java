package com.udacity.jwdnd.course1.cloudstorage.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ResultScreen extends BaseScreen {

    public ResultScreen(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="messageText")
    private WebElement messageTextInput;

    @FindBy(id="messageType")
    private WebElement messageTypeOption;

    public boolean isResultScreenLoaded(){
        WebElement messageTextInput = wait.until(webDriver -> webDriver.findElement(By.id("messageText")));
        return messageTextInput.isDisplayed();
    }

    public void goToHomeScreen(String msg){
        messageTextInput.sendKeys(msg);
        messageTextInput.submit();
    }
}
