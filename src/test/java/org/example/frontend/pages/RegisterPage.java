package org.example.frontend.pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "h2.text-center.mb-4")
    private WebElement registrationHeader;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }

    public boolean isAt() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(registrationHeader)).isDisplayed() &&
                   registrationHeader.getText().equals("Регистрация");
        } catch (TimeoutException e) {
            return false;
        }
    }
}
