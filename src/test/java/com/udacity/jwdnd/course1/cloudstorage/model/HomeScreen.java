package com.udacity.jwdnd.course1.cloudstorage.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomeScreen extends BaseScreen {

    public HomeScreen(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="logout-button")
    private WebElement logoutButton;

    @FindBy(id="nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id="nav-credential-tab")
    private WebElement credentialsTab;

    @FindBy(id="add-note-button")
    private WebElement addNoteButton;

    @FindBy(id="add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id="edit-note-button")
    public WebElement editNoteButton;

    @FindBy(id="delete-note-button")
    public WebElement deleteNoteButton;

    @FindBy(id="edit-credential-button")
    private WebElement editCredentialeButton;

    @FindBy(id="delete-credential-button")
    private WebElement deleteCredentialButton;

    @FindBy(id="note-title-label")
    public WebElement noteTitleLabel;

    @FindBy(id="note-description-label")
    public WebElement noteDescriptionLabel;

    @FindBy(id="url-label")
    public WebElement urlLabel;

    @FindBy(id="user-label")
    public WebElement userLabel;

    @FindBy(id="password-label")
    public WebElement passwordLabel;

    public boolean isScreenLoaded(){
        WebElement logoutButton = wait.until(webDriver -> webDriver.findElement(By.id("logout-button")));
        return logoutButton.isDisplayed();
    }

    public boolean isNotesTabLoaded(){
        WebElement addNoteButton = wait.until(webDriver -> webDriver.findElement(By.id("add-note-button")));
        return addNoteButton.isDisplayed();
    }

    public boolean isCredentialsTabLoaded(){
        WebElement addCredentialButton = wait.until(webDriver -> webDriver.findElement(By.id("add-credential-button")));
        return addCredentialButton.isDisplayed();
    }

    public void logout(){
        logoutButton.click();
    }

    public void goToNotes(){
        notesTab.click();
    }

    public void openNewNoteModal(){
        addNoteButton.click();
    }

    public void openEditNoteModal(){
        editNoteButton.click();
    }

    public void tapDeleteNoteButton(){
        deleteNoteButton.click();
    }

}
