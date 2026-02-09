package pro.ra_tech.websites_watcher.core.webdriver.impl;

import lombok.val;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pro.ra_tech.websites_watcher.core.webdriver.api.WebdriverManager;

public class WebdriverManagerImpl implements WebdriverManager {
    public WebdriverManagerImpl(String chromeDriverPath) {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
    }

    @Override
    public WebDriver buildHeadlessChromeDriver() {
        val options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");

        return new ChromeDriver();
    }
}
