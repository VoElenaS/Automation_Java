package org.example.frontend.pages;


import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@Data
public class ProductPage implements HasNavigationBar {

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

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(this.driver, this);
    }

    public void searchProductByName(String productName) {
        waitUntilVisible(searchProductForm).clear();
        searchProductForm.sendKeys(productName);
    }

    public void clickSearch() {
        waitUntilClickable(searchProductButton).click();
    }

    public boolean isProductDisplayed(String productName) {
        for (WebElement row : tableProductsRows) {
            if (row.getText().contains(productName)) {
                return true;
            }
        }
        return false;
    }

    private WebElement waitUntilClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private WebElement waitUntilVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

}
