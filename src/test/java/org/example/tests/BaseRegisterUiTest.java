package org.example.tests;

import org.example.db.DBUtils;
import org.example.frontend.DriverFactory;
import org.example.frontend.models.User;
import org.example.utils.TestProperties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

public class BaseRegisterUiTest {
    protected WebDriver driver;
    protected User testUser;

    public static final String REGISTER_ENDPOINT_UI_URL = TestProperties.properties.getProperty("register_endpoint_ui_url");

    @BeforeEach
    public void setUpUI(TestInfo testInfo) {
        String browserName = testInfo.getDisplayName();
        driver = DriverFactory.getDriver(DriverFactory.Browsers.valueOf(browserName));
        driver.get(REGISTER_ENDPOINT_UI_URL);
    }

    @AfterEach
    public void tearDownUI() {
        driver.quit();
    }

    @AfterAll
    static void tearDown() {
        DBUtils.closeConnection();
    }

}


