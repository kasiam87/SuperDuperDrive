package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.model.HomeScreen;
import com.udacity.jwdnd.course1.cloudstorage.model.LoginScreen;
import com.udacity.jwdnd.course1.cloudstorage.model.SignUpScreen;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotesTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private SignUpScreen signUpScreen;
    private LoginScreen loginScreen;
    private HomeScreen homeScreen;

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        signUpScreen = new SignUpScreen(driver);
        loginScreen = new LoginScreen(driver);
        homeScreen = new HomeScreen(driver);
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
        loginScreen.login("user", "password");
        assertTrue("Home screen was not loaded!", homeScreen.isScreenLoaded());
    }

    private void signUp(){
        loginScreen.gotToSignUpScreen();
        assertTrue("SignUp screen didn't load", signUpScreen.isSignUpScreenLoaded());
        signUpScreen.signUp("name", "name", "user", "password");
        assertTrue("Signup failed!", signUpScreen.isSignUpSuccessful());
    }
    private void logout() {
        homeScreen.logout();
        assertTrue("Login screen was not loaded!", new LoginScreen(driver).isScreenLoaded());
    }

    @Test
    public void createNote(){
        homeScreen.goToNotes();
        homeScreen.addNote("Some title", "Some description");
        homeScreen.sleep(5000);
    }

//    @Test
//    public void editNote(){
//
//    }
//
//    @Test
//    public void deleteNote(){
//
//    }
}
