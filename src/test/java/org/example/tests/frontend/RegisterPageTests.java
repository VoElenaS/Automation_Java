package org.example.tests.frontend;

import org.example.db.models.UserDB;
import org.example.frontend.DriverFactory;
import org.example.frontend.UiTest;
import org.example.frontend.UiUtils;
import org.example.frontend.pages.RegisterPage;
import org.example.models.generators.UserDataGenerator;
import org.example.models.request.RegisterRequest;
import org.example.tests.BaseRegisterUiTest;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RegisterPageTests extends BaseRegisterUiTest {

    private RegisterPage registerPage;

    @BeforeEach
    void setUp() {
        registerPage = new RegisterPage(driver);
    }

    @UiTest
    void userRegistration(DriverFactory.Browsers browsers) throws InterruptedException {
        RegisterRequest registeredUser = UserDataGenerator.generate();
        registerPage.registerAs(registeredUser);
        String actual = registeredUser.getName();
        UserDB expected = UiUtils.waitForUserByName(actual, 10, 300);

        assertNotNull(expected, "User not found in DB after registration");
        assertEquals(expected.getName(), actual);
    }

}