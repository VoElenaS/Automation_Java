package org.example.frontend.pages;

import org.example.frontend.UiUtils;
import org.example.models.request.ProductRequest;
import org.junit.platform.commons.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateProductForm extends BasePage {

    @FindBy(css = ".modal-title#addProductModalLabel")
    public WebElement headerAddProduct;

    @FindBy(xpath = "//button[contains(text(), 'Создать продукт')]")
    public WebElement btnCreateProduct;

    @FindBy(css = "#nameError")
    public WebElement nameError;

    @FindBy(css = "#descriptionError")
    public WebElement descriptionError;

    @FindBy(css = "#categoryError")
    public WebElement categoryError;

    @FindBy(css = "input[type='text'].form-control#add-name")
    private WebElement productName;

    @FindBy(css = "input[type='text'].form-control#add-description")
    private WebElement productDescription;

    @FindBy(css = "input[type='text'].form-control#add-category")
    private WebElement productCategory;

    @FindBy(css = "input[type='text'].form-control#add-price")
    private WebElement productPrice;

    @FindBy(css = "input[type='number'].form-control#add-stock-quantity")
    private WebElement productQuantity;

    @FindBy(css = "input[type='file'].form-control#add-image-url")
    private WebElement productImage;

    @FindBy(css = "input[type='number'].form-control#add-weight")
    private WebElement productWeight;

    @FindBy(css = "input[type='text'].form-control#add-dimensions")
    private WebElement productDimensions;

    @FindBy(css = "input[type='text'].form-control#add-manufacturer")
    private WebElement productManufacturer;

    @FindBy(css = "#addProductModal .btn-close")
    private WebElement closeBtn;

    public CreateProductForm(WebDriver driver) {
        super(driver);
        init();
    }

    public void createProduct(ProductRequest product) {
        waitUntilSuppliersLoaded();
        setProductName(product.getName())
                .setProductCategory(product.getCategory())
                .setProductDescription(product.getDescription())
                .setProductDimensions(product.getDimensions())
                .setProductPrice(product.getPrice())
                .setProductImage(product.getImageUrl())
                .setProductQuantity(product.getStockQuantity())
                .setProductWeight(product.getWeight())
                .setSupplierId(product.getSupplierId())
                .setProductManufacturer(product.getManufacturer());
        UiUtils.scrollBottomRightCornerToElement(driver);
        clickProductCreate();
    }

    public String getErrorMessage(String fieldId) {
        try {
            WebElement errElement = driver.findElement(By.cssSelector("#" + fieldId + "Error"));
            return errElement.isDisplayed() ? errElement.getText().trim() : "";
        } catch (Exception e) {
            return "";
        }
    }

    public String getNameError() {
        return getErrorMessage("name");
    }

    public String getDescriptionError() {
        return getErrorMessage("description");
    }

    public String getCategoryError() {
        return getErrorMessage("category");
    }

    public boolean isNameErrorVisible() {
        try {
            return nameError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isDescriptionErrorVisible() {
        try {
            return descriptionError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCategoryErrorVisible() {
        try {
            return categoryError.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public CreateProductForm setProductName(String name) {
        if (StringUtils.isNotBlank(name)) {
            waitUntilVisible(productName).clear();
            productName.sendKeys(name);
        }
        return this;
    }

    public CreateProductForm setProductDescription(String description) {
        if (StringUtils.isNotBlank(description)) {
            waitUntilVisible(productDescription).clear();
            productDescription.sendKeys(description);
        }
        return this;
    }

    public CreateProductForm setProductCategory(String category) {
        if (StringUtils.isNotBlank(category)) {
            waitUntilVisible(productCategory).clear();
            productCategory.sendKeys(category);
        }
        return this;
    }

    public CreateProductForm setProductPrice(String price) {
        waitUntilVisible(productPrice).clear();
        productPrice.sendKeys(price);
        return this;
    }

    public CreateProductForm setProductQuantity(int quantity) {
        waitUntilVisible(productQuantity).clear();
        productQuantity.sendKeys(String.valueOf(quantity));
        return this;
    }

    public CreateProductForm setSupplierId(String supplierId) {
        waitUntilSuppliersLoaded();
        By supplierDropdownLocator = By.cssSelector("#add-supplier-id");
        WebElement dropdown = waitUntilVisible(driver.findElement(supplierDropdownLocator));
        dropdown.click();
        Select select = new Select(driver.findElement(supplierDropdownLocator));
        select.selectByValue(supplierId);
        return this;
    }

    public void waitUntilSuppliersLoaded() {
        By supplierDropdownLocator = By.cssSelector("#add-supplier-id");
        By addNewProductBtnLocator = By.cssSelector("#add-new-product-btn");

        int maxRetries = 5;
        WebDriverWait waitShort = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebDriverWait waitLong = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                boolean ready = waitLong.until(d -> {
                    try {
                        WebElement dropdown = d.findElement(supplierDropdownLocator);
                        Select supplierSelect = new Select(dropdown);
                        return supplierSelect.getOptions().size() > 1
                               && "Выберите поставщика".equals(supplierSelect.getOptions().get(0).getText());
                    } catch (Exception e) {
                        return false;
                    }
                });

                if (ready) {
                    return;
                }
            } catch (Exception e) {
            }
            try {
                waitShort.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
                waitShort.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("#addProductModal")));
                WebElement addNewProductBtn = waitShort.until(ExpectedConditions.elementToBeClickable(addNewProductBtnLocator));
                addNewProductBtn.click();
                waitShort.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#addProductModal")));
            } catch (Exception ex) {
                System.out.println("Error during modal reopen: " + ex.getMessage());
            }
        }
        throw new RuntimeException("Supplier dropdown did not load after " + maxRetries + " attempts");
    }

    public CreateProductForm setProductImage(String imgPath) {
        if (StringUtils.isNotBlank(imgPath)) {
            waitUntilVisible(productImage).sendKeys(imgPath);
        }
        return this;
    }

    public CreateProductForm setProductWeight(String weight) {
        if (StringUtils.isNotBlank(weight)) {
            waitUntilVisible(productWeight).clear();
            productWeight.sendKeys(weight);
        }
        return this;
    }

    public CreateProductForm setProductDimensions(String dimensions) {
        if (StringUtils.isNotBlank(dimensions)) {
            waitUntilVisible(productDimensions).clear();
            productDimensions.sendKeys(dimensions);
        }
        return this;
    }

    public CreateProductForm setProductManufacturer(String manufacturer) {
        if (StringUtils.isNotBlank(manufacturer)) {
            waitUntilVisible(productManufacturer).clear();
            productManufacturer.sendKeys(manufacturer);
        }
        return this;
    }

    public void clickProductCreate() {
        waitUntilClickable(btnCreateProduct).click();
    }
}
