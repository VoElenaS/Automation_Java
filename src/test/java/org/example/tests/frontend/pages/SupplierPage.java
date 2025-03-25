package org.example.tests.frontend.pages;


import lombok.Data;
import org.example.tests.frontend.pages.elements.SuppliersTableRow;
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
    List<WebElement> suppliersTabelRows;

    @FindBy(xpath = "//*[contains(text(), 'Поставщик успешно удален')]")
    WebElement deleteSupplierNotification;


    public SupplierPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public SuppliersTableRow getTableRowByName(String name) {
        return suppliersTabelRows.stream()
                .map(SuppliersTableRow::new)
                .filter(row -> row.getName().equals(name))
                .findFirst().orElseThrow();
    }

    public boolean isSupplierExistOnThePage(String name) {

        return suppliersTabelRows.stream()
                .map(SuppliersTableRow::new)
                .map(row -> row.getName().equals(name))
                .toList().contains(name);
    }

    public boolean isDeletedSupplirNotoficationDisplaied() {  // ✅ Fixed method name
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOf(deleteSupplierNotification));
            return deleteSupplierNotification.isDisplayed();
        } catch (Exception e) {
            return false;  // Return false if element is not found or not visible
        }
    }


}
