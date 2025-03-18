package org.example.tests.frontend;

import org.example.backend.models.LoginRequest;
import org.example.backend.models.RegisterRequest;
import org.example.backend.models.SupplierCreateModel;
import org.example.tests.BaseTest;
import org.example.tests.frontend.models.User;
import org.example.tests.frontend.pages.LoginPage;
import org.example.tests.frontend.pages.ProductPage;
import org.example.tests.frontend.pages.SupplierPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.example.backend.requests.ProductsServiceRequests.executePostCreateSupllier;
import static org.junit.jupiter.api.Assertions.*;

public class StoreManagerAutoUiTests extends BaseTest {

    User testUser;
    WebDriver driver;

    @BeforeEach
    void setUpDate() {

        testUser = registerTestUser();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(API_UI_URL);

    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void loginTest() {
        new LoginPage(driver).loginAs(testUser);
        ProductPage productPage = new ProductPage(driver);
        assertTrue(productPage.isPersonalAccountDisplayed());
        System.out.println();
    }

    @Test
    void deleteSupplierTest() {


        String accessToken = authServiceRequests.postLogin(LoginRequest.builder()
                .email(testUser.getEmail())
                .password(testUser.getPassword())
                .build()).getAccessToken();

        SupplierCreateModel createdSupplier = executePostCreateSupllier(SupplierCreateModel.generate(), accessToken);

        new LoginPage(driver).loginAs(testUser);
        new ProductPage(driver).clickSuppliersLink();

        SupplierPage supplierPage = new SupplierPage(driver);
        SupplierCreateModel actualSuppllier = supplierPage.getTableRowByName(createdSupplier.getName()).getSupplierCreateModel();

        assertEquals(createdSupplier, actualSuppllier);

        supplierPage.getTableRowByName(createdSupplier.getName()).clickDeleteButton();
        driver.switchTo().alert().accept();
        assertTrue(supplierPage.isDeletedSupplirNotoficationDisplaied());

        assertFalse(supplierPage.isSupplierExistOnThePage(createdSupplier.getName()));

    }

    private User registerTestUser() {
        RegisterRequest registerRequest = RegisterRequest.generate();
        authServiceRequests.registerUser(registerRequest);
        return User.builder().email(registerRequest.getEmail()).password(registerRequest.getPassword()).build();
    }

}
