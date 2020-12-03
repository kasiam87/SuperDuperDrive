package com.udacity.jwdnd.course1.cloudstorage.tests;

import com.udacity.jwdnd.course1.cloudstorage.model.HomeScreen;
import com.udacity.jwdnd.course1.cloudstorage.model.LoginScreen;
import com.udacity.jwdnd.course1.cloudstorage.model.SignUpScreen;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    @BeforeEach
    public void beforeEach() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.get(String.format("http://localhost:%d/login", port));
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void unauthorizedUserCannotAccessHomeScreen(){
        driver.get(String.format("http://localhost:%d/home", port));
        assertFalse(driver.getCurrentUrl().endsWith("home"), "Current url should not be /home");
        assertTrue(driver.getCurrentUrl().endsWith("login"), "Current url should be /login");
    }

    @Test
    public void unauthorizedUserCannotAccessResultScreen(){
        driver.get(String.format("http://localhost:%d/result", port));
        assertFalse(driver.getCurrentUrl().endsWith("result"), "Current url should not be /result");
        assertTrue(driver.getCurrentUrl().endsWith("login"), "Current url should be /login");
    }

    @Test
    public void loginInLogoutTest(){
        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.gotToSignUpScreen();
        SignUpScreen signUpScreen = new SignUpScreen(driver);
        assertTrue(signUpScreen.isSignUpScreenLoaded(), "SignUp screen didn't load");
        signUpScreen.signUp("name", "name", "user", "password");
        assertTrue(loginScreen.isScreenLoaded(), "Signup failed!");
        loginScreen.login("user", "password");
        HomeScreen homeScreen = new HomeScreen(driver);
        assertTrue(homeScreen.isScreenLoaded(), "Home screen was not loaded!");
        assertTrue(driver.getCurrentUrl().endsWith("home"), "Current url should be /home");
        homeScreen.logout();
        assertTrue(loginScreen.isScreenLoaded(), "Login screen was not loaded!");
        assertTrue(driver.getCurrentUrl().endsWith("login"), "Current url should be /home");

        driver.get(String.format("http://localhost:%d/home", port));
        assertFalse(driver.getCurrentUrl().endsWith("home"), "Current url should not be /home");
        assertTrue(driver.getCurrentUrl().endsWith("login"), "Current url should be /login");
    }
}