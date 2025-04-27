package org.example.tests.frontend;

import org.example.frontend.DriverFactory;
import org.example.frontend.models.User;
import org.example.frontend.pages.LoginPage;
import org.example.frontend.pages.ProductPage;
import org.example.frontend.pages.SupplierPage;
import org.example.models.generators.SupplierDataGenerator;
import org.example.models.generators.UserDataGenerator;
import org.example.models.request.RegisterRequest;
import org.example.models.request.SupplierRequest;
import org.example.models.response.SupplierResponse;
import org.example.tests.BaseUiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class StoreManagerAutoUiTests extends BaseUiTest {
    User testUser;

    @BeforeEach
    void setUpDate() {
        testUser = registerTestUser();
        driver.get(API_UI_URL);
    }

    @ParameterizedTest
    @EnumSource(DriverFactory.Browsers.class)
    void loginTest(DriverFactory.Browsers browser) {
        new LoginPage(driver).loginAs(testUser);
        ProductPage productPage = new ProductPage(driver);
        assertTrue(productPage.isPersonalAccountDisplayed());
        System.out.println();
    }

    @Test
    void deleteSupplierTest() {
        SupplierResponse createdSupplier = suppliersServicesAPI.createSupplier(SupplierDataGenerator.generate(), accessToken);

        new LoginPage(driver).loginAs(testUser);
        new ProductPage(driver).clickSuppliersLink();

        SupplierPage supplierPage = new SupplierPage(driver);
        SupplierRequest actualSupplier = supplierPage.getTableRowByName(createdSupplier.getName()).getSupplierCreateModel();

        assertEquals(createdSupplier.getName(), actualSupplier.getName());

        supplierPage.getTableRowByName(createdSupplier.getName()).clickDeleteButton();
        driver.switchTo().alert().accept();
        assertTrue(supplierPage.isDeletedSupplierNotificationDisplayed());

        assertFalse(supplierPage.isSupplierExistOnThePage(createdSupplier.getName()));

    }

    private User registerTestUser() {
        RegisterRequest registerRequest = UserDataGenerator.generate();
        authServiceAPI.registerUser(registerRequest);
        return User.builder().email(registerRequest.getEmail()).password(registerRequest.getPassword()).build();
    }

}
