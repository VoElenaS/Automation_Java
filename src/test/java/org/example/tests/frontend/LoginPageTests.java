package org.example.tests.frontend;

import org.example.frontend.DriverFactory;
import org.example.frontend.UiTest;
import org.example.frontend.UiUtils;
import org.example.frontend.models.User;
import org.example.frontend.pages.LoginPage;
import org.example.frontend.pages.ProductPage;
import org.example.frontend.pages.RegisterPage;
import org.example.models.generators.UserDataGenerator;
import org.example.tests.BaseUiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginPageTests extends BaseUiTest {

    private LoginPage loginPage;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage(driver);
    }

    @UiTest
    void loginTest(DriverFactory.Browsers browser) {
        loginPage.loginAs(testUser);
        ProductPage productPage = new ProductPage(driver);
        assertTrue(productPage.isPersonalAccountDisplayed());
    }

    @UiTest
    void loginTestWithRememberMe(DriverFactory.Browsers browser) throws InterruptedException {
        loginPage.loginAs(testUser, true);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//nav//a[contains(., 'Личный кабинет')]")));
        Cookie refreshCookie = driver.manage().getCookieNamed("refresh_token");
        assertNotNull(refreshCookie, "Refresh token should be present with checkbox RememberMe");

        String userId = UiUtils.getFromLocalStorage(driver, "user_id");
        driver.quit();
        driver = DriverFactory.getDriver(browser);
        driver.get(API_UI_URL);
        if (driver instanceof FirefoxDriver) {
            driver.manage().addCookie(new Cookie("refresh_token", refreshCookie.getValue(), "/"));
        } else {
            driver.manage().addCookie(refreshCookie);
        }
        UiUtils.setValueInLocalStorage(driver, "user_id", userId);
        driver.navigate().refresh();
        ProductPage productPage = new ProductPage(driver);
        new WebDriverWait(driver, Duration.ofSeconds(300))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//nav//a[contains(., 'Личный кабинет')]")));

        assertTrue(productPage.isPersonalAccountDisplayed());
    }

    @UiTest
    @DisplayName("loginWithWrongPassword")
    void loginWithWrongPassword(DriverFactory.Browsers browser) {
        User user = User.builder().email(UserDataGenerator.generateEmail()).password("1").build();
        loginPage.loginAs(user);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.alertIsPresent());

        assertTrue(loginPage.isAlertPresent(driver), "Expected alert on wrong login");
    }

    @UiTest
    @DisplayName("loginWithWrongEmailFormat")
    void loginWithWrongEmailFormat(DriverFactory.Browsers browser) {
        User user = User.builder().email("email").password(UserDataGenerator.generatePassword()).build();
        loginPage.loginAs(user);

        assertFalse(loginPage.isEmailFormatValid(), "Expected alert on wrong login");
    }

    @UiTest
    @DisplayName("goToRegistrationPage")
    void goToRegistrationPage(DriverFactory.Browsers browser) throws InterruptedException {
        loginPage.clickRegisterLink();
        RegisterPage registerPage = new RegisterPage(driver);

        assertTrue(registerPage.isAt(), "Should be on Registration Page with header 'Регистрация'");
    }
}
