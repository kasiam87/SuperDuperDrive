package com.udacity.jwdnd.course1.cloudstorage.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CredentialModal extends BaseScreen {

    public CredentialModal(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="credential-url")
    private WebElement urlField;

    @FindBy(id="credential-username")
    private WebElement userField;

    @FindBy(id="credential-password")
    private WebElement passwordField;

    @FindBy(id="save-credential-button")
    private WebElement saveCredentialButton;

    @FindBy(id="edit-credential-url")
    private WebElement urlEditField;

    @FindBy(id="edit-credential-username")
    private WebElement userEditField;

    @FindBy(id="edit-credential-password")
    private WebElement passwordEditField;

    @FindBy(id="save-edit-credential-button")
    private WebElement saveEditCredentialButton;

    public void enterCredentialAndSave(String url, String user, String password) {
        WebElement urlField = wait.until(webDriver -> webDriver.findElement(By.id("credential-url")));
        urlField.sendKeys(url);
        userField.sendKeys(user);
        passwordField.sendKeys(password);
        saveCredentialButton.click();
    }

    public void editCredentialAndSave(String url, String user, String password) {
        WebElement urlEditField = wait.until(webDriver -> webDriver.findElement(By.id("edit-credential-url")));
        urlEditField.sendKeys(url);
        userEditField.sendKeys(user);
        passwordEditField.sendKeys(password);
        saveEditCredentialButton.click();
    }
}
