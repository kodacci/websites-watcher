package pro.ra_tech.websites_watcher.core.webdriver.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import pro.ra_tech.websites_watcher.core.webdriver.api.WebdriverManager;

import java.net.URI;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class WebdriverManagerImpl implements WebdriverManager {
    private final String remoteChromeSeleniumUrl;

    @Override
    public WebDriver buildHeadlessChromeDriver() {
        val options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");

        val caps = new DesiredCapabilities(Map.of(
                ChromeOptions.CAPABILITY, options
        ));

        try {
            log.info("Connecting to remote Chrome Selenium server: {}", remoteChromeSeleniumUrl);

            return new RemoteWebDriver(new URI(remoteChromeSeleniumUrl).toURL(), caps);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
