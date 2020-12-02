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
        noteTitleField.sendKeys(title);
        noteDescriptionField.sendKeys(description);
        saveNoteChangesButton.click();
    }

    public void editNoteAndSave(String title, String description) {
        WebElement editNoteTitleField = wait.until(webDriver -> webDriver.findElement(By.id("edit-note-title")));
        editNoteTitleField.sendKeys(title);
        editNoteDescriptionField.sendKeys(description);
        saveEditNoteChangesButton.click();
    }
}
