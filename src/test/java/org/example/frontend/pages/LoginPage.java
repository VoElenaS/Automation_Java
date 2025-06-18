package org.example.frontend.pages;

import org.example.frontend.models.User;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    @FindBy(css = "#email")
    private WebElement emailField;

    @FindBy(css = "#password")
    private WebElement passwordField;

    @FindBy(css = "button.btn")
    private WebElement submitButton;

    @FindBy(css = "input.form-check-input#rememberMe")
    private WebElement rememberMeCheckBox;

    @FindBy(css = "label.form-check-label[for='rememberMe']")
    private WebElement rememberMeLabel;

    @FindBy(css = "a[href='/register']")
    private WebElement registerLink;

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void loginAs(User user) {
        enterEmail(user.getEmail())
                .enterPassword(user.getPassword())
                .clickSubmit();
    }

    public void loginAs(User user, boolean rememberMe) {
        enterEmail(user.getEmail())
                .enterPassword(user.getPassword())
                .toggleRememberMe(rememberMe)
                .clickSubmit();
    }

    public LoginPage enterEmail(String email) {
        waitUntilVisible(emailField).clear();
        emailField.sendKeys(email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        waitUntilVisible(passwordField).clear();
        passwordField.sendKeys(password);
        return this;
    }

    public void clickSubmit() {
        waitUntilClickable(submitButton).click();
    }

    public void clickRegisterLink() {
        waitUntilClickable(registerLink).click();
    }

    public LoginPage toggleRememberMe(boolean check) {
        waitUntilVisible(rememberMeCheckBox);
        if (rememberMeCheckBox.isSelected() != check) {
            rememberMeCheckBox.click();
        }
        return this;
    }

    private WebElement waitUntilVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    private WebElement waitUntilClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean isLoginButtonVisible() {
        try {
            return waitUntilVisible(submitButton).isDisplayed();
        } catch (NoSuchElementException e) {
            System.err.println("Submit button not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isAlertPresent(WebDriver driver) {
        try {
            Alert alert = driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public boolean isEmailFormatValid() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean isValid;
        try {
            Object result = js.executeScript("return document.getElementById('email').checkValidity()");
            isValid = result != null && (Boolean) result;
        } catch (JavascriptException e) {
            throw new RuntimeException("Javascript execution failed", e);
        }
        return isValid;
    }

}
