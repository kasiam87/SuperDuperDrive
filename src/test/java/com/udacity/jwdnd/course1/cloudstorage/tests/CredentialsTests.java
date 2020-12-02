package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModal;
import com.udacity.jwdnd.course1.cloudstorage.model.HomeScreen;
import com.udacity.jwdnd.course1.cloudstorage.model.LoginScreen;
import com.udacity.jwdnd.course1.cloudstorage.model.ResultScreen;
import com.udacity.jwdnd.course1.cloudstorage.model.SignUpScreen;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CredentialsTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private SignUpScreen signUpScreen;
    private LoginScreen loginScreen;
    private HomeScreen homeScreen;
    private CredentialModal credentialModal;
    private ResultScreen resultScreen;

    private static final String USER = "credential-test-user";
    private static final String PASSWORD = "credential-test-password";
    private static final String URL = "http://gmail.com";
    private static final String CREDENTIAL_USER = "user";
    private static final String CREDENTIAL_PASSWORD = "user";

    private static final String SUFFIX = "2";
    private static final String EDITED_CREDENTIAL_USER = CREDENTIAL_USER + SUFFIX;

    @BeforeAll
    public void beforeAll() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        signUpScreen = new SignUpScreen(driver);
        loginScreen = new LoginScreen(driver);
        homeScreen = new HomeScreen(driver);
        credentialModal = new CredentialModal(driver);
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
    public void createCredential() {
        homeScreen.goToCredentials();
        assertTrue(homeScreen.isCredentialsTabLoaded(), "Credential tab was not loaded!");
        homeScreen.openNewCredentialModal();
        credentialModal.enterCredentialAndSave(URL, CREDENTIAL_USER, CREDENTIAL_PASSWORD);
        assertTrue(resultScreen.isResultScreenLoaded(), "Result screen was not loaded!");
        resultScreen.goToHomeScreen();
        assertTrue(driver.getCurrentUrl().endsWith("home"), "Current url should be /home");
        homeScreen.goToCredentials();
        assertTrue(homeScreen.isCredentialsTabLoaded(), "Credential tab was not loaded!");
        assertTrue(homeScreen.urlLabel.getText().equalsIgnoreCase(URL), "Incorrect url displayed");
        assertTrue(homeScreen.userLabel.getText().equalsIgnoreCase(CREDENTIAL_USER), "Incorrect username displayed");
    }

    @Test
    @Order(2)
    public void editCredential() {
        homeScreen.goToCredentials();
        assertTrue(homeScreen.isCredentialsTabLoaded(), "Credential tab was not loaded!");
        homeScreen.openEditCredentialModal();
        credentialModal.editCredentialAndSave("", SUFFIX, "");
        assertTrue(resultScreen.isResultScreenLoaded(), "Result screen was not loaded!");
        resultScreen.goToHomeScreen();
        assertTrue(driver.getCurrentUrl().endsWith("home"), "Current url should be /home");
        homeScreen.goToCredentials();
        assertTrue(homeScreen.isCredentialsTabLoaded(), "Credential tab was not loaded!");
        assertTrue(homeScreen.urlLabel.getText().equalsIgnoreCase(URL), "Incorrect url displayed");
        assertTrue(homeScreen.userLabel.getText().equalsIgnoreCase(EDITED_CREDENTIAL_USER), "Incorrect user displayed");
    }

    @Test
    @Order(3)
    public void deleteCredential() {
        homeScreen.goToCredentials();
        assertTrue(homeScreen.isCredentialsTabLoaded(), "Credential tab was not loaded!");
        homeScreen.tapDeleteCredentialButton();
        assertTrue(resultScreen.isResultScreenLoaded(), "Result screen was not loaded!");
        resultScreen.goToHomeScreen();
        homeScreen.goToCredentials();
        assertTrue(homeScreen.isCredentialsTabLoaded(), "Credential tab was not loaded!");

        assertTrue(driver.getCurrentUrl().endsWith("home"), "Current url should be /home");
        assertThrows(NoSuchElementException.class, () -> homeScreen.userLabel.isDisplayed());
    }
}
