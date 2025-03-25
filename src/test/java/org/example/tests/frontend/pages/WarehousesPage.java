package org.example.tests.frontend.pages;


import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Data

public class WarehousesPage implements HasNavigationBar {

    WebDriver driver;

    public WarehousesPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

}
