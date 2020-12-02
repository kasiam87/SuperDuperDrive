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

    private static final String USER = "edit-test-user";
    private static final String PASSWORD = "edit-test-password";
    private static final String NOTE_TITLE = "Some title";
    private static final String NOTE_DESCRIPTION = "Some description";

    private static final String SUFFIX = "(2)";
    private static final String EDITED_NOTE_TITLE = NOTE_TITLE + SUFFIX;
    private static final String EDITED_NOTE_DESCRIPTION = NOTE_DESCRIPTION + SUFFIX;

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
    }

    @AfterEach
    public void afterEach() {
        logout();
    }

    private void login() {
        loginScreen.login(USER, PASSWORD);
        assertTrue(homeScreen.isScreenLoaded(), "Home screen was not loaded!");
    }

    private void signUp() {
        loginScreen.gotToSignUpScreen();
        assertTrue(signUpScreen.isSignUpScreenLoaded(), "SignUp screen didn't load");
        signUpScreen.signUp("name", "name", USER, PASSWORD);
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
        noteModal.enterNoteAndSave(NOTE_TITLE, NOTE_DESCRIPTION);
        assertTrue(resultScreen.isResultScreenLoaded(), "Result screen was not loaded!");
        resultScreen.goToHomeScreen();
        assertTrue(driver.getCurrentUrl().endsWith("home"), "Current url should be /home");
    }

    @Test
    @Order(2)
    public void editNote() {
        homeScreen.goToNotes();
        assertTrue(homeScreen.isNotesTabLoaded(), "Notes tab was not loaded!");
        homeScreen.openEditNoteModal();
        noteModal.editNoteAndSave(SUFFIX, SUFFIX);
        assertTrue(resultScreen.isResultScreenLoaded(), "Result screen was not loaded!");
        resultScreen.goToHomeScreen();
        homeScreen.goToNotes();
        assertTrue(driver.getCurrentUrl().endsWith("home"), "Current url should be /home");
        assertTrue(homeScreen.noteTitleLabel.getText().equalsIgnoreCase(EDITED_NOTE_TITLE), "Incorrect title displayed");
        assertTrue(homeScreen.noteDescriptionLabel.getText().equalsIgnoreCase(EDITED_NOTE_DESCRIPTION), "Incorrect description displayed");
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
