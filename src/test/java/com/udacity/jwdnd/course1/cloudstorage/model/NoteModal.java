package com.udacity.jwdnd.course1.cloudstorage.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NoteModal extends BaseScreen {

    public NoteModal(WebDriver driver) {
        super(driver);
    }

    @FindBy(id="note-title")
    private WebElement noteTitleField;

    @FindBy(id="note-description")
    private WebElement noteDescriptionField;

    @FindBy(id="save-note-changes")
    private WebElement saveNoteChangesButton;

    @FindBy(id="edit-note-title")
    private WebElement editNoteTitleField;

    @FindBy(id="edit-note-description")
    private WebElement editNoteDescriptionField;

    @FindBy(id="save-edit-note-changes")
    private WebElement saveEditNoteChangesButton;

    public void enterNoteAndSave(String title, String description) {
        WebElement noteTitleField = wait.until(webDriver -> webDriver.findElement(By.id("note-title")));
        sleep(1000);
        noteTitleField.sendKeys(title);
        sleep(1000);
        noteDescriptionField.sendKeys(description);
        sleep(1000);
        saveNoteChangesButton.click();
    }

    public void editNoteAndSave(String title, String description) {
        WebElement editNoteTitleField = wait.until(webDriver -> webDriver.findElement(By.id("edit-note-title")));
        sleep(1000);
        editNoteTitleField.sendKeys(title);
        sleep(1000);
        editNoteDescriptionField.sendKeys(description);
        sleep(1000);
        saveEditNoteChangesButton.click();
    }
}
