package org.example.frontend.pages;

import lombok.Data;
import org.example.frontend.pages.elements.SuppliersTableRow;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

@Data
public class SupplierPage implements HasNavigationBar {
    private final WebDriver driver;

    private final By supplierRowsLocator = By.cssSelector("table#suppliers-table tbody tr");
    private final By deleteNotificationLocator = By.xpath("//*[contains(text(), 'Поставщик успешно удален')]");

    public SupplierPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public List<WebElement> getSuppliersRows() {
        return driver.findElements(supplierRowsLocator);
    }

    public SuppliersTableRow getRowByName(String name) {
        String xpath = String.format(
                "//table[@id='suppliers-table']//tr[td[1][normalize-space(text())='%s']]",
                name
        );
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(driver ->
                    ((JavascriptExecutor) driver)
                            .executeScript("return document.readyState")
                            .equals("complete")
            );

            // Add a short pause before searching (only for debugging)
            Thread.sleep(1000);  // <-- Here, 500 milliseconds pause

            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

            return new SuppliersTableRow(element);
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Supplier not found: " + name + " | XPath: " + xpath, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }


    public boolean isDeletedSupplierNotificationDisplayed() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement notification = wait.until(ExpectedConditions.visibilityOfElementLocated(deleteNotificationLocator));
            return notification.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSupplierRemoved(String name) {
        String xpath = String.format(
                "//table[@id='suppliers-table']//tr[td[1][normalize-space(text())='%s']]",
                name
        );
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            // Wait until the row is not found in the DOM
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException e) {
            return false; // If timeout occurs, the element is still visible
        }
    }

}
