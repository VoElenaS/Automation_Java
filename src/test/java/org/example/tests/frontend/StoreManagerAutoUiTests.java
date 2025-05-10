package org.example.tests.frontend;

import org.example.frontend.DriverFactory;
import org.example.frontend.UiTest;
import org.example.frontend.UiUtils;
import org.example.frontend.models.User;
import org.example.frontend.pages.LoginPage;
import org.example.frontend.pages.ProductPage;
import org.example.frontend.pages.SupplierPage;
import org.example.models.generators.ProductDataGenerator;
import org.example.models.generators.SupplierDataGenerator;
import org.example.models.generators.UserDataGenerator;
import org.example.models.request.RegisterRequest;
import org.example.models.request.SupplierRequest;
import org.example.models.response.ProductResponse;
import org.example.models.response.SupplierResponse;
import org.example.tests.BaseUiTest;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StoreManagerAutoUiTests extends BaseUiTest {
    User testUser;

    @BeforeEach
    void setUpDate() {
        testUser = registerTestUser();
        driver.get(API_UI_URL);
    }

    @UiTest
    void loginTest(DriverFactory.Browsers browser) {
        new LoginPage(driver).loginAs(testUser);
        ProductPage productPage = new ProductPage(driver);
        assertTrue(productPage.isPersonalAccountDisplayed());
    }

    //???
    @UiTest
    void loginTestWithRememberMe(DriverFactory.Browsers browser) throws InterruptedException {
        //enableDevTools();
        new LoginPage(driver).loginAs(testUser, true);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//nav//a[contains(., 'Личный кабинет')]")));
        Set<Cookie> cookies = driver.manage().getCookies();
        System.out.println("Number of cookies: " + cookies.size());

        Cookie refreshToken = null;

        for (Cookie cookie : cookies) {
            if ("refresh_token".equals(cookie.getName())) {
                refreshToken = cookie;
                System.out.println("refreshToken name: " + refreshToken.getName());
                System.out.println("refreshToken value: " + refreshToken.getValue());
            }
        }

        assertNotNull(refreshToken, "Refresh token should be present with checkbox RememberMe");

        String userId = UiUtils.getFromLocalStorage(driver, "user_id");


        driver.quit();
        driver = DriverFactory.getDriver(browser);
        //enableDevTools();
        driver.get(API_UI_URL);
        //driver.manage().addCookie(refreshToken);
        if (driver instanceof FirefoxDriver) {
            driver.manage().addCookie(new Cookie("refresh_token", refreshToken.getValue(), "/"));
        } else {
            driver.manage().addCookie(refreshToken);
        }
        UiUtils.setValueInLocalStorage(driver, "user_id", userId);
        //driver.get(API_UI_URL);
        driver.navigate().refresh();

        ProductPage productPage = new ProductPage(driver);
        new WebDriverWait(driver, Duration.ofSeconds(300))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//nav//a[contains(., 'Личный кабинет')]")));
        assertTrue(productPage.isPersonalAccountDisplayed());
    }

    @UiTest
    void deleteSupplierTest(DriverFactory.Browsers browser) {
        SupplierResponse createdSupplier = suppliersServicesAPI.createSupplier(SupplierDataGenerator.generate(), accessToken);
        new LoginPage(driver).loginAs(testUser);

        SupplierPage supplierPage = new SupplierPage(driver);
        supplierPage.clickSuppliersLink();
        SupplierRequest actualSupplier = supplierPage.getTableRowByName(createdSupplier.getName()).getSupplierCreateModel();
        assertEquals(createdSupplier.getName(), actualSupplier.getName());

        UiUtils.scrollBottomRightCornerToElement(driver);
        supplierPage.getTableRowByName(createdSupplier.getName()).clickDeleteButton();
        driver.switchTo().alert().accept();

        assertTrue(supplierPage.isDeletedSupplierNotificationDisplayed());
        assertTrue(supplierPage.isSupplierRemovedFromThePage(createdSupplier.getName()));
    }

    @UiTest
    void searchProductByName(DriverFactory.Browsers browser) throws InterruptedException {
        SupplierResponse supplierResponse = suppliersServicesAPI.createSupplier(SupplierDataGenerator.generate(), accessToken);
        ProductResponse productResponse = productsServicesAPI
                .createProduct(ProductDataGenerator.generate(supplierResponse.getSupplierId()), accessToken);
        new LoginPage(driver).loginAs(testUser);

        ProductPage productPage = new ProductPage(driver);
        productPage.clickProductsLink();
        productPage.searchProductByName(productResponse.getName());
        productPage.clickSearch();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("table#products-table"), productResponse.getName()));
        boolean productDisplayed = productPage.isProductDisplayed(productResponse.getName());

        assertTrue(productDisplayed, "Product should be displayed in the table.");
    }

    private User registerTestUser() {
        RegisterRequest registerRequest = UserDataGenerator.generate();
        authServiceAPI.registerUser(registerRequest);
        return User.builder().email(registerRequest.getEmail()).password(registerRequest.getPassword()).build();
    }

}
