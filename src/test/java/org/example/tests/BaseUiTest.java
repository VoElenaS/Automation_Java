package org.example.tests;

import org.example.frontend.DriverFactory;
import org.example.frontend.models.User;
import org.example.models.generators.UserDataGenerator;
import org.example.models.request.RegisterRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;

public class BaseUiTest extends BaseApiTest {
    protected WebDriver driver;
    protected User testUser;

//    public void enableDevTools() {
//        DevTools devTools = ((HasDevTools) driver).getDevTools();
//        devTools.createSession();
//
//        // Enable network logging
//        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
//        //devTools.send(Console.enable());
//
//        // Add event listeners for requests and responses
//        devTools.addListener(Network.requestWillBeSent(), request -> {
//            Request req = request.getRequest();
//            System.out.println("Request URL: " + req.getUrl());
//            System.out.println("Method: " + req.getMethod());
//            System.out.println("Headers: " + req.getHeaders());
//        });
//
//        devTools.addListener(Network.responseReceived(), response -> {
//            Response res = response.getResponse();
//            System.out.println("Response URL: " + res.getUrl());
//            System.out.println("Status: " + res.getStatus());
//            System.out.println("Headers: " + res.getHeaders());
//        });
//
//        devTools.addListener(Console.messageAdded(), response -> {
//            String res = response.getText();
//            System.out.println("Console: " + res);
//        });
//    }

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
}


