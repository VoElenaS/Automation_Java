package org.example.frontend.pages;

import org.example.models.request.RegisterRequest;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    @FindBy(css = "h2.text-center.mb-4")
    private WebElement registrationHeader;

    @FindBy(css = "#username")
    private WebElement userNameField;

    @FindBy(css = "#email")
    private WebElement emailField;

    @FindBy(css = "#password")
    private WebElement passwordFiled;

    @FindBy(css = "[type=\"submit\"]")
    private WebElement btnSubmit;

    @FindBy(css = "a[href='/login']")
    private WebElement enterLink;

    public RegisterPage(WebDriver driver) {
        super(driver);
        init();
    }

    public void registerAs(RegisterRequest registerUser) {
        enterUserName(registerUser.getName())
                .enterEmail(registerUser.getEmail())
                .enterPassword(registerUser.getPassword())
                .clickSubmit();
    }

    public RegisterPage enterUserName(String userName) {
        waitUntilVisible(userNameField).clear();
        userNameField.sendKeys(userName);
        return this;
    }

    public RegisterPage enterEmail(String email) {
        waitUntilVisible(emailField).clear();
        emailField.sendKeys(email);
        return this;
    }

    public RegisterPage enterPassword(String password) {
        waitUntilVisible(passwordFiled).clear();
        passwordFiled.sendKeys(password);
        return this;
    }

    public RegisterPage clickSubmit() {
        waitUntilClickable(btnSubmit).click();
        return this;
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
