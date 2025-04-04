package org.example.tests.frontend.pages;

import org.example.tests.frontend.models.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    WebDriver driver;

    @FindBy(css = "#email")
    WebElement emailFiled;
    @FindBy(css = "#password")
    WebElement passwordFiled;
    @FindBy(css = "button.btn")
    WebElement submitButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }


    public void loginAs(User user) {
        emailFiled.sendKeys(user.getEmail());
        passwordFiled.sendKeys(user.getPassword());
        submitButton.click();
    }
}
