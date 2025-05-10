package org.example.frontend.pages;


import lombok.Data;
import org.example.frontend.pages.elements.SuppliersTableRow;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@Data

public class SupplierPage implements HasNavigationBar {
    WebDriver driver;

    @FindBy(css = "table#suppliers-table tbody tr")
    List<WebElement> suppliersTableRows;

    @FindBy(xpath = "//*[contains(text(), 'Поставщик успешно удален')]")
    WebElement deleteSupplierNotification;

    public SupplierPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public SuppliersTableRow getTableRowByName(String name) {
        return suppliersTableRows.stream()
                .map(SuppliersTableRow::new)
                .filter(row -> row.getName().equals(name))
                .findFirst().orElseThrow();
    }

    public boolean isDeletedSupplierNotificationDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOf(deleteSupplierNotification));
            return deleteSupplierNotification.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSupplierRemovedFromThePage(String name) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(driver1 -> {
            try {
                return suppliersTableRows.stream()
                        .map(SuppliersTableRow::new)
                        .noneMatch(row -> row.getName().equals(name));
            } catch (Exception e) {
                return false;
            }
        });
    }

}
