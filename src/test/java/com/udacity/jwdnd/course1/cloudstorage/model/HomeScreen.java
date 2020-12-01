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

    @FindBy(id="note-title")
    private WebElement noteTitleField;

    @FindBy(id="note-description")
    private WebElement noteDescriptionField;

    @FindBy(id="save-note-changes")
    private WebElement saveNoteChangesButton;

    public boolean isScreenLoaded(){
        WebElement logoutButton = wait.until(webDriver -> webDriver.findElement(By.id("logout-button")));
        return logoutButton.isDisplayed();
    }

    public void logout(){
        logoutButton.click();
    }

    public void goToNotes(){
        notesTab.click();
    }

    public void addNote(String title, String description){
        addNoteButton.click();
        WebElement noteTitleField = wait.until(webDriver -> webDriver.findElement(By.id("note-title")));
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        saveNoteChangesButton.click();
    }
}
