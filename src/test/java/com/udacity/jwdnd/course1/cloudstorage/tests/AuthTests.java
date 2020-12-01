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

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

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
        assertFalse("Current url should not be /home", driver.getCurrentUrl().endsWith("home"));
        assertTrue("Current url should be /login", driver.getCurrentUrl().endsWith("login"));
    }

    @Test
    public void unauthorizedUserCannotAccessResultScreen(){
        driver.get(String.format("http://localhost:%d/result", port));
        assertFalse("Current url should not be /result", driver.getCurrentUrl().endsWith("result"));
        assertTrue("Current url should be /login", driver.getCurrentUrl().endsWith("login"));
    }

    @Test
    public void loginInLogoutTest(){
        LoginScreen loginScreen = new LoginScreen(driver);
        loginScreen.gotToSignUpScreen();
        SignUpScreen signUpScreen = new SignUpScreen(driver);
        assertTrue("SignUp screen didn't load", signUpScreen.isSignUpScreenLoaded());
        signUpScreen.signUp("name", "name", "user", "password");
        assertTrue("Signup failed!", signUpScreen.isSignUpSuccessful());
        signUpScreen.goToLoginScreen();
        loginScreen.login("user", "password");
        HomeScreen homeScreen = new HomeScreen(driver);
        assertTrue("Home screen was not loaded!", homeScreen.isScreenLoaded());
        assertTrue("Current url should be /home", driver.getCurrentUrl().endsWith("home"));
        homeScreen.logout();
        assertTrue("Login screen was not loaded!", loginScreen.isScreenLoaded());
        assertTrue("Current url should be /home", driver.getCurrentUrl().endsWith("login"));

        driver.get(String.format("http://localhost:%d/home", port));
        assertFalse("Current url should not be /home", driver.getCurrentUrl().endsWith("home"));
        assertTrue("Current url should be /login", driver.getCurrentUrl().endsWith("login"));
    }
}
