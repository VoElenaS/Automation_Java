package org.example.tests;

import org.example.frontend.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

public class BaseUiTest extends BaseApiTest {
    protected WebDriver driver;

    @BeforeEach
    public void setUpUI(TestInfo testInfo) {
        int spaceIndex = testInfo.getDisplayName().lastIndexOf(' ');
        String browserName = testInfo.getDisplayName().substring(spaceIndex + 1);
        driver = DriverFactory.getDriver(DriverFactory.Browsers.valueOf(browserName));
    }

    @AfterEach
    public void tearDownUI() {
        driver.quit();
    }
}
