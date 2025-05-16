package org.example.frontend;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UiUtils {
    public static String getFromLocalStorage(WebDriver driver, String itemKey) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String itemValue = (String) js.executeScript("return window.localStorage.getItem(arguments[0]);", itemKey);
        System.out.println("Value for key '" + itemKey + "': " + itemValue);
        return itemValue;
    }

    public static void setValueInLocalStorage(WebDriver driver, String key, String value) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.setItem(arguments[0], arguments[1]);", key, value);
        System.out.println("Stored Value for '" + key + "': " + value);
    }

    public static void scrollBottomRightCornerToElement(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(document.body.scrollWidth, document.body.scrollHeight);");
    }

    public static void waitVisible(WebElement webElement, WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void waitUntilElementVisible(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("Page loaded completely");
    }
}

