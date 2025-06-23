package org.example.frontend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductPage extends BasePage implements HasNavigationBar {

    @FindBy(css = "#add-new-product-btn")
    private WebElement addProductButton;

    @FindBy(css = "button.btn.btn-outline-secondary[onclick=\"searchProduct()\"]")
    private WebElement searchProductButton;

    @FindBy(css = "input.form-control#search-name")
    private WebElement searchProductForm;

    @FindBy(css = "button.btn.btn-sm.btn-outline-warning.mt-2")
    private WebElement editProductButton;

    @FindBy(css = "//button[text()='Удалить']")
    private WebElement deleteProductButton;

    @FindBy(css = "table#products-table tr")
    private List<WebElement> tableProductsRows;

    public ProductPage(WebDriver driver) {
        super(driver);
        init();
    }

    public void searchProductByName(String productName) {
        waitUntilVisible(searchProductForm).clear();
        searchProductForm.sendKeys(productName);
    }

    public void clickAddProduct() {
        waitUntilClickable(addProductButton).click();
    }

    public void clickSearch() {
        waitUntilClickable(searchProductButton).click();
    }

    public By getProductNameBy(String productName) {
        return By.xpath("//a[@class='product-name' and text()='" + productName + "']/ancestor::tr");
    }

    public boolean isProductDisplayed(String productName) {
        try {
            for (WebElement row : tableProductsRows) {
                if (row.getText().contains(productName)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

}
