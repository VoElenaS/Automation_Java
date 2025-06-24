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
    private final String format = "//table[@id='suppliers-table']//tr[td[1][normalize-space(text())='%s']]";

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
        String rowXPath = String.format(format, name);

        WebElement table = driver.findElement(By.id("suppliers-table"));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(1));
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        longWait.until(ExpectedConditions.presenceOfElementLocated(By.id("suppliers-table")));

        int scrollAttempts = 0;
        int maxScrolls = 50;

        while (scrollAttempts < maxScrolls) {
            try {
                WebElement row = shortWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(rowXPath)));
                longWait.until(ExpectedConditions.visibilityOf(row));
                js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", row);
                return new SuppliersTableRow(row);
            } catch (TimeoutException e) {
                js.executeScript("arguments[0].scrollTop += 300;", table);
                scrollAttempts++;
            }
        }
        throw new NoSuchElementException("Supplier not found after scrolling: " + name);
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
        String xpath = String.format(format, name);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        } catch (TimeoutException e) {
            return false;
        }
    }

}
