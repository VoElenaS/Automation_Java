package org.example.frontend;

import org.example.db.UsersQueries;
import org.example.db.models.UserDB;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
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

    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'center'});", element);
    }

    public static void scrollToBottom(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight);");
    }


    public static void waitVisible(WebElement webElement, WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(webElement));
    }

    public static void waitUntilElementVisible(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("Page loaded completely");
    }

    public static UserDB waitForUserByName(String name, int maxAttempts, int delayMs) throws InterruptedException {
        UserDB user = null;
        for (int i = 0; i < maxAttempts; i++) {
            user = UsersQueries.userByName(name);
            if (user != null) break;
            Thread.sleep(delayMs);
        }
        return user;
    }

    public static void smartClick(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String browser = ((RemoteWebDriver) driver).getCapabilities().getBrowserName().toLowerCase();
        if (browser.contains("firefox")) {
            js.executeScript("arguments[0].click();", element);
        } else {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(element))
                    .click();
        }
    }
}

