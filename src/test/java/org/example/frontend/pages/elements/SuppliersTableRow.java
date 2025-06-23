package org.example.frontend.pages.elements;

import org.example.frontend.UiUtils;
import org.example.models.request.SupplierRequest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;

public class SuppliersTableRow {
    WebElement root;

    private final By supplierRowsLocator = By.cssSelector("table#suppliers-table tbody tr");

    public SuppliersTableRow(WebElement root) {
        this.root = root;
    }

    public String getName() {
        return root.findElement(By.xpath(".//td[1]")).getText();
    }

    public WebElement getDeleteBtn() {
        return root.findElement(By.cssSelector("button.btn-outline-danger"));
    }

    public SupplierRequest getSupplierCreateModel() {
        return SupplierRequest.builder()
                .name(root.findElement(By.xpath(".//td[1]")).getText())
                .contactName(root.findElement(By.xpath(".//td[2]")).getText())
                .contactEmail(root.findElement(By.xpath(".//td[3]")).getText())
                .phoneNumber(root.findElement(By.xpath(".//td[4]")).getText())
                .address(root.findElement(By.xpath(".//td[5]")).getText())
                .country(root.findElement(By.xpath(".//td[6]")).getText())
                .city(root.findElement(By.xpath(".//td[7]")).getText())
                .website(root.findElement(By.xpath(".//td[8]")).getText())
                .build();
    }

    public void clickEditButton() {
        root.findElement(By.cssSelector("button.btn-outline-warning")).click();
    }

    public void clickDeleteBtn() {
        WebElement deleteBtn = root.findElement(By.cssSelector("button.btn-outline-danger"));
        WebDriver driver = ((WrapsDriver) root).getWrappedDriver();
        UiUtils.smartClick(driver, deleteBtn);
    }

}
