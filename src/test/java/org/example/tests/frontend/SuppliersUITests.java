package org.example.tests.frontend;

import org.example.frontend.DriverFactory;
import org.example.frontend.UiTest;
import org.example.frontend.pages.LoginPage;
import org.example.frontend.pages.SupplierPage;
import org.example.frontend.pages.elements.SuppliersTableRow;
import org.example.models.generators.SupplierDataGenerator;
import org.example.models.response.SupplierResponse;
import org.example.services.SuppliersServicesAPI;
import org.example.tests.BaseUiTest;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.NoAlertPresentException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SuppliersUITests extends BaseUiTest {
    static String supplierId;
    static String supplierName;

    @BeforeEach
    void createSupplier() {
        SuppliersServicesAPI suppliersServicesAPI = new SuppliersServicesAPI();
        SupplierResponse supplier = suppliersServicesAPI.createSupplier(SupplierDataGenerator.generate(), accessToken);
        supplierId = supplier.getSupplierId();
        supplierName = supplier.getName();
    }

    @UiTest
    void deleteSupplier(DriverFactory.Browsers browsers) throws InterruptedException {
        new LoginPage(driver).loginAs(testUser);
        SupplierPage supplierPage = new SupplierPage(driver);
        supplierPage.clickSuppliersLink();
        SuppliersTableRow rowByName = supplierPage.getRowByName(supplierName);
        rowByName.clickDeleteBtn();
        try {
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException ignored) {
        }

        assertTrue(supplierPage.isDeletedSupplierNotificationDisplayed());
        assertTrue(supplierPage.isSupplierRemoved(supplierName));
    }
}
