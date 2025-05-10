package org.example.frontend.pages;

import org.example.models.request.ProductRequest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreateProductForm {

    WebDriver driver;

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

    @FindBy(css = ".form-control#add-supplier-id")
    private WebElement supplierId;

    @FindBy(css = "input[type='file'].form-control#add-image-url")
    private WebElement productImage;

    @FindBy(css = "input[type='number'].form-control#add-weight")
    private WebElement productWeight;

    @FindBy(css = "input[type='text'].form-control#add-dimensions")
    private WebElement productDimensions;

    @FindBy(css = "input[type='text'].form-control#add-manufacturer")
    private WebElement productManufacturer;

    @FindBy(xpath = "//button[contains(text(), 'Создать продукт')]")
    public WebElement btnCreateProduct;


    public CreateProductForm(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
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
                .setProductManufacturer(product.getManufacturer())
                .clickProductCreate();
    }

    public CreateProductForm setProductName(String name) {
        productName.clear();
        productName.sendKeys(name);
        return this;
    }

    public CreateProductForm setProductDescription(String description) {
        productDescription.clear();
        productDescription.sendKeys(description);
        return this;
    }

    public CreateProductForm setProductCategory(String category) {
        productCategory.clear();
        productCategory.sendKeys(category);
        return this;
    }

    public CreateProductForm setProductPrice(String price) {
        productPrice.clear();
        productPrice.sendKeys(price);
        return this;
    }

    public CreateProductForm setProductQuantity(int quantity) {
        productQuantity.clear();
        productQuantity.sendKeys(String.valueOf(quantity));
        return this;
    }

    public CreateProductForm setSupplierId(String supplier) {
        Select supplierSelect = new Select(supplierId);
        supplierSelect.selectByValue(supplier);
        return this;
    }

    public CreateProductForm setProductImage(String imgPath) {
        productImage.sendKeys(imgPath);
        return this;
    }

    public CreateProductForm setProductWeight(String weight) {
        productWeight.clear();
        productWeight.sendKeys(weight);
        return this;
    }

    public CreateProductForm setProductDimensions(String dimensions) {
        productDimensions.clear();
        productDimensions.sendKeys(dimensions);
        return this;
    }

    public CreateProductForm setProductManufacturer(String manufacturer) {
        productManufacturer.clear();
        productManufacturer.sendKeys(manufacturer);
        return this;
    }

    public void clickProductCreate() {
        btnCreateProduct.click();
    }
}
