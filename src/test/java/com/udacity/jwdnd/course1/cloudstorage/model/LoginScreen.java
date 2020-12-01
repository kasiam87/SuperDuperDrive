package com.udacity.jwdnd.course1.cloudstorage.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginScreen extends BaseScreen {

    public LoginScreen(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="inputUsername")
    private WebElement usernameInput;

    @FindBy(id="inputPassword")
    private WebElement passwordInput;

    @FindBy(id="submit-button")
    private WebElement submitButton;

    @FindBy(id="signup-link")
    private WebElement signUpLink;

    public boolean isScreenLoaded(){
        WebElement usernameInput = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        return usernameInput.isDisplayed();
    }

    public void login(String username, String password){
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
    }

    public void gotToSignUpScreen(){
        signUpLink.click();
    }
}
