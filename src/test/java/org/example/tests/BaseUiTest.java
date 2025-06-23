package org.example.tests;

import org.example.frontend.DriverFactory;
import org.example.frontend.models.User;
import org.example.models.generators.UserDataGenerator;
import org.example.models.request.RegisterRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseUiTest extends BaseApiTest {
    protected WebDriver driver;
    protected User testUser;

    @BeforeEach
    public void setUpUI(TestInfo testInfo) {
        String browserName = testInfo.getDisplayName();
        driver = DriverFactory.getDriver(DriverFactory.Browsers.valueOf(browserName));
        testUser = registerTestUser();
        driver.get(API_UI_URL);
    }

    @AfterEach
    public void tearDownUI() {
        driver.quit();
    }

    private User registerTestUser() {
        RegisterRequest registerRequest = UserDataGenerator.generate();
        authServiceAPI.registerUser(registerRequest);
        return User.builder().email(registerRequest.getEmail()).password(registerRequest.getPassword()).build();
    }

    protected WebDriverWait getWait(Duration time) {
        return new WebDriverWait(driver, time);
    }

    protected WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}


