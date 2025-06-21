package org.example.frontend.pages;

import org.example.frontend.UiUtils;
import org.example.models.request.ProductRequest;
import org.junit.platform.commons.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
    @FindBy(css = "#add-supplier-id")
    private WebElement supplierId;
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

    public CreateProductForm setSupplierId(String supplier) {
        Select supplierSelect = new Select(waitUntilClickable(supplierId));
        wait.until(d -> {
            try {
                supplierSelect.selectByValue(supplier);
                return true;
            } catch (Exception e) {
                return false;
            }
        });

        return this;
    }

    public void waitUntilSuppliersLoaded(ProductPage productPage) {
        Select supplierSelect = new Select(waitUntilClickable(supplierId));
        waitUntilClickable(closeBtn);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(d -> {
            try {
                boolean isLoaded = !supplierSelect.getOptions().isEmpty() && "Выберите поставщика".equals(supplierSelect.getOptions().getFirst().getText());
                System.out.println(isLoaded);
                if (isLoaded) {
                    return true;
                }
                System.out.println("Closing");
                closeBtn.click();
                System.out.println("Closed");
                productPage.clickAddProduct();
                System.out.println("Opening");
                UiUtils.waitVisible(headerAddProduct, driver);
                System.out.println("Opened");
                return false;
            } catch (Exception e) {
                return false;
            }
        });
    }
//    public CreateProductForm setSupplierId(String supplierId) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("loadSuppliers('#add-supplier-id');");
//
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#add-supplier-id")));
//
//        WebElement dropdown = driver.findElement(By.cssSelector("#add-supplier-id"));
//        Select select = new Select(dropdown);
//
//        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#add-supplier-id option"), 1));
//
//        int attempts = 0;
//        while (attempts < 3) {
//            try {
//                List<WebElement> options = select.getOptions();
//                for (WebElement option : options) {
//                    if (option.getAttribute("value").equals(supplierId)) {
//                        select.selectByValue(supplierId);
//                        System.out.println("Selected supplier: " + supplierId);
//                        return this;
//                    }
//                }
//                break;
//            } catch (StaleElementReferenceException e) {
//                System.out.println("StaleElementReferenceException encountered, retrying...");
//                attempts++;
//                dropdown = driver.findElement(By.cssSelector("#add-supplier-id"));
//                select = new Select(dropdown);
//                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#add-supplier-id option"), 1));
//            }
//        }
//
//        System.out.println("Supplier '" + supplierId + "' not found in the dropdown!");
//        return this;
//    }

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
