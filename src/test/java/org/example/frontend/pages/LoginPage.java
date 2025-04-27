package org.example.frontend.pages;

import org.example.frontend.models.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(css = "#email")
    WebElement emailFiled;
    @FindBy(css = "#password")
    WebElement passwordFiled;
    @FindBy(css = "button.btn")
    WebElement submitButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void loginAs(User user) {
        emailFiled.sendKeys(user.getEmail());
        passwordFiled.sendKeys(user.getPassword());
        submitButton.click();
    }
}
