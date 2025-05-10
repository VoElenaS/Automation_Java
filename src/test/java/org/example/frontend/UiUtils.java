package org.example.frontend;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

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
}
