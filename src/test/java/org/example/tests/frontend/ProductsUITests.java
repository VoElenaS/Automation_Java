package org.example.tests.frontend;

import org.example.frontend.DriverFactory;
import org.example.frontend.UiTest;
import org.example.frontend.UiUtils;
import org.example.frontend.pages.CreateProductForm;
import org.example.frontend.pages.LoginPage;
import org.example.frontend.pages.ProductPage;
import org.example.frontend.pages.SupplierPage;
import org.example.models.generators.ProductDataGenerator;
import org.example.models.generators.SupplierDataGenerator;
import org.example.models.request.ProductRequest;
import org.example.models.request.SupplierRequest;
import org.example.models.response.ProductResponse;
import org.example.models.response.SupplierResponse;
import org.example.services.SuppliersServicesAPI;
import org.example.tests.BaseUiTest;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsUITests extends BaseUiTest {
    static String supplierId;
    static String supplierName;

    @BeforeAll
    static void createSupplier() {
        SuppliersServicesAPI suppliersServicesAPI = new SuppliersServicesAPI();
        SupplierResponse supplier = suppliersServicesAPI.createSupplier(SupplierDataGenerator.generate(), accessToken);
        supplierId = supplier.getSupplierId();
        supplierName = supplier.getName();
    }

    @UiTest
    void deleteSupplierTest(DriverFactory.Browsers browser) {
        new LoginPage(driver).loginAs(testUser);

        SupplierPage supplierPage = new SupplierPage(driver);
        supplierPage.clickSuppliersLink();
        SupplierRequest actualSupplier = supplierPage.getTableRowByName(supplierName).getSupplierCreateModel();
        assertEquals(supplierName, actualSupplier.getName());

        UiUtils.scrollBottomRightCornerToElement(driver);
        supplierPage.getTableRowByName(supplierName).clickDeleteButton();
        driver.switchTo().alert().accept();

        assertTrue(supplierPage.isDeletedSupplierNotificationDisplayed());
        assertTrue(supplierPage.isSupplierRemovedFromThePage(supplierName));
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
        String productName = productResponse.getName();
        By productLocator = By.xpath("//a[@class='product-name' and text()='" + productName + "']/ancestor::tr");
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(productLocator));
        boolean productDisplayed = productPage.isProductDisplayed(productResponse.getName());

        assertTrue(productDisplayed, "Product should be displayed in the table.");
    }

    @UiTest
    void createProduct(DriverFactory.Browsers browser) throws InterruptedException {
        SupplierResponse supplierResponse = suppliersServicesAPI.createSupplier(SupplierDataGenerator.generate(), accessToken);
        new LoginPage(driver).loginAs(testUser);
        ProductPage productPage = new ProductPage(driver);
        productPage.clickAddProduct();

        CreateProductForm createProductForm = new CreateProductForm(driver);
        UiUtils.waitVisible(createProductForm.headerAddProduct, driver);
        createProductForm.waitUntilSuppliersLoaded();  // <-- FIXED here: removed argument

        String imagePath = new File("src/test/resources/img/foto.jpeg").getAbsolutePath();
        ProductRequest product = ProductDataGenerator.generate(supplierResponse.getSupplierId(), imagePath);
        createProductForm.createProduct(product);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(createProductForm.headerAddProduct)));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-outline-secondary[onclick=\"searchProduct()\"]"))).click();
        productPage.searchProductByName(product.getName());
        productPage.clickSearch();
        boolean productDisplayed = wait.until(d -> productPage.isProductDisplayed(product.getName()));

        assertTrue(productDisplayed, "Product should be displayed in the table.");
    }

    @UiTest
    public void createProductWithInvalidName(DriverFactory.Browsers browsers) {
        SupplierResponse supplier = suppliersServicesAPI.createSupplier(SupplierDataGenerator.generate(), accessToken);
        new LoginPage(driver).loginAs(testUser);
        ProductPage productPage = new ProductPage(driver);
        productPage.clickAddProduct();

        CreateProductForm createProductForm = new CreateProductForm(driver);
        ProductRequest product = ProductDataGenerator.generateOnlyMandatoryFields(supplier.getSupplierId());
        product.setName("ab"); // invalid name

        createProductForm.createProduct(product); // this calls clickProductCreate() internally, no need to call again

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#nameError")));

        String actualError = createProductForm.getNameError();
        String expectedError = "Название продукта обязательно, от 3 до 100 символов, только буквы и цифры.";

        assertEquals(expectedError, actualError, "The error message for product name is incorrect");
        assertTrue(createProductForm.isNameErrorVisible(), "The error message should be visible for wrong product name");
    }


    @UiTest
    void createProductWithInvalidDescription(DriverFactory.Browsers browsers) {
        SupplierResponse supplier = suppliersServicesAPI.createSupplier(SupplierDataGenerator.generate(), accessToken);
        new LoginPage(driver).loginAs(testUser);
        ProductPage productPage = new ProductPage(driver);
        productPage.clickAddProduct();
        CreateProductForm createProductForm = new CreateProductForm(driver);
        ProductRequest product = ProductDataGenerator.generateOnlyMandatoryFields(supplier.getSupplierId());
        StringBuilder newDescription = new StringBuilder();
        while (newDescription.length() < 501) {
            newDescription.append(UUID.randomUUID().toString().replace("-", ""));
        }
        product.setDescription(newDescription.toString());
        createProductForm.createProduct(product);
        createProductForm.clickProductCreate();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(createProductForm.descriptionError));

        String actualError = createProductForm.getDescriptionError();
        String expectedError = "Описание не должно превышать 500 символов.";

        assertEquals(expectedError, actualError, "The error massage for product name is incorrect");
        assertTrue(createProductForm.isDescriptionErrorVisible(), "The error message should be visible for wrong product name");
    }

}
