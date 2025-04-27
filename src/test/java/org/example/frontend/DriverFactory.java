package org.example.frontend;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverFactory {
    public enum Browsers {
        CHROME, EDGE, FIREFOX
    }

    public static WebDriver getDriver(Browsers browser) {
        WebDriver webDriver =
                switch (browser) {
                    case CHROME -> new ChromeDriver();
                    case EDGE -> new EdgeDriver();
                    case FIREFOX -> new FirefoxDriver();
                };
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return webDriver;
    }
}
