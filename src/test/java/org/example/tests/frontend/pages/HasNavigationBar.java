package org.example.tests.frontend.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public interface HasNavigationBar {

    WebDriver getDriver();

    default boolean isPersonalAccountDisplayed() {
        return getDriver().findElement(By.xpath("//nav//a[contains(., 'Личный кабинет')]")).isDisplayed();
    }

    default void clickProductsLink() {
        getDriver().findElement(By.xpath("//nav//a[contains(., 'Продукты')]")).click();
    }

    default void clickSuppliersLink() {
        getDriver().findElement(By.xpath("//nav//a[contains(., 'Поставщики')]")).click();
    }

    default void clickWarehousesLink() {
        getDriver().findElement(By.xpath("//nav//a[contains(., 'Склады')]")).click();
    }

}
