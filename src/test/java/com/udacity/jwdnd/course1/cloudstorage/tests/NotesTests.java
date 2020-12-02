package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.model.HomeScreen;
import com.udacity.jwdnd.course1.cloudstorage.model.LoginScreen;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModal;
import com.udacity.jwdnd.course1.cloudstorage.model.ResultScreen;
import com.udacity.jwdnd.course1.cloudstorage.model.SignUpScreen;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotesTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private SignUpScreen signUpScreen;
    private LoginScreen loginScreen;
    private HomeScreen homeScreen;
    private NoteModal noteModal;
    private ResultScreen resultScreen;

    private static final String noteTitle = "Some title";
    private static final String noteDescription = "Some description";

    private static final String suffix = "(2)";
    private static final String editedTitle = noteTitle + suffix;
    private static final String editedDescription = noteDescription + suffix;

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        signUpScreen = new SignUpScreen(driver);
        loginScreen = new LoginScreen(driver);
        homeScreen = new HomeScreen(driver);
        noteModal = new NoteModal(driver);
        resultScreen = new ResultScreen(driver);
        driver.get(String.format("http://localhost:%d/login", port));
        signUp();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
        driver = null;
    }

    @BeforeEach
    public void beforeEach() {
        driver.get(String.format("http://localhost:%d/login", port));
        login();
        homeScreen.sleep(2000);
    }

    @AfterEach
    public void afterEach() {
        logout();
    }

    private void login() {
        loginScreen.login("user", "password");
        assertTrue(homeScreen.isScreenLoaded(), "Home screen was not loaded!");
    }

    private void signUp() {
        loginScreen.gotToSignUpScreen();
        assertTrue(signUpScreen.isSignUpScreenLoaded(), "SignUp screen didn't load");
        signUpScreen.signUp("name", "name", "user", "password");
        assertTrue(signUpScreen.isSignUpSuccessful(), "Signup failed!");
    }

    private void logout() {
        homeScreen.logout();
        assertTrue(new LoginScreen(driver).isScreenLoaded(), "Login screen was not loaded!");
    }

    @Test
    @Order(1)
    public void createNote() {
        homeScreen.goToNotes();
        homeScreen.openNewNoteModal();
        noteModal.enterNoteAndSave(noteTitle, noteDescription);
        assertTrue(resultScreen.isResultScreenLoaded(), "Result screen was not loaded!");
        resultScreen.goToHomeScreen();
        assertTrue(driver.getCurrentUrl().endsWith("home"), "Current url should be /home");
        homeScreen.sleep(1000);
    }

    @Test
    @Order(2)
    public void editNote() {
        homeScreen.goToNotes();
        assertTrue(homeScreen.isNotesTabLoaded(), "Notes tab was not loaded!");
        homeScreen.sleep(1000);
        homeScreen.openEditNoteModal();
        noteModal.editNoteAndSave(suffix, suffix);
        assertTrue(resultScreen.isResultScreenLoaded(), "Result screen was not loaded!");
        resultScreen.goToHomeScreen();
        homeScreen.goToNotes();
        assertTrue(driver.getCurrentUrl().endsWith("home"), "Current url should be /home");
        assertTrue(homeScreen.noteTitleLabel.getText().equalsIgnoreCase(editedTitle), "Incorrect title displayed");
        assertTrue(homeScreen.noteDescriptionLabel.getText().equalsIgnoreCase(editedDescription), "Incorrect description displayed");
    }

    @Test
    @Order(3)
    public void deleteNote() {
        homeScreen.goToNotes();
        assertTrue(homeScreen.isNotesTabLoaded(), "Notes tab was not loaded!");
        homeScreen.tapDeleteNoteButton();
        assertTrue(resultScreen.isResultScreenLoaded(), "Result screen was not loaded!");
        resultScreen.goToHomeScreen();
        homeScreen.goToNotes();

        assertTrue(driver.getCurrentUrl().endsWith("home"), "Current url should be /home");

        assertThrows(NoSuchElementException.class, () -> homeScreen.noteTitleLabel.isDisplayed());
    }
}
