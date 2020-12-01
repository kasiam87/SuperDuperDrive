package com.udacity.jwdnd.course1.cloudstorage.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpScreen extends BaseScreen {

    public SignUpScreen(WebDriver driver) {
        super(driver);
    }

    private WebElement firstNameInput(){
        return wait.until(webDriver -> webDriver.findElement(By.id("inputFirstName")));
    }

    @FindBy(css="#inputLastName")
    private WebElement lastNameInput;

    @FindBy(css="#inputUsername")
    private WebElement usernameInput;

    @FindBy(css="#inputPassword")
    private WebElement passwordInput;

    @FindBy(id="submit-button")
    private WebElement submitButton;

    @FindBy(css="#success-msg")
    private WebElement successMsg;

    @FindBy(css="#login-link")
    private WebElement loginLink;

    public boolean isSignUpScreenLoaded(){
        return firstNameInput().isDisplayed();
    }

    public void signUp(String firstname, String lastname, String username, String password){
        firstNameInput().sendKeys(firstname);
        lastNameInput.sendKeys(lastname);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
    }

    public boolean isSignUpSuccessful(){
        WebElement successMsg = wait.until(webDriver -> webDriver.findElement(By.id("success-msg")));
        return successMsg.isDisplayed();
    }

    public void goToLoginScreen(){
        loginLink.click();
    }
}
