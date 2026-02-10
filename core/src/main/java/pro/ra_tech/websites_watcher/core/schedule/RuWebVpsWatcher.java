package pro.ra_tech.websites_watcher.core.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.scheduling.annotation.Scheduled;
import pro.ra_tech.websites_watcher.core.schedule.api.WebSiteWatcher;
import pro.ra_tech.websites_watcher.core.webdriver.api.WebdriverManager;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class RuWebVpsWatcher implements WebSiteWatcher {
    private final String baseUrl;
    private final String planName;
    private final WebdriverManager webdriverManager;

    @Override
    @Scheduled(cron = "${app.watchers.ru-web.cron}", zone = "Europe/Moscow")
    public void check() {
        WebDriver driver = null;

        try {
            log.info("Checking RuWeb VPS {} in Netherlands", planName);

            driver = webdriverManager.buildHeadlessChromeDriver();

            log.info("Opening RuWeb VPS page");
            driver.get(baseUrl);

            log.info("Searching for nano card");
            driver.findElements(By.xpath("//p[contains(@class, 'tariff-card__title') and text()='" + planName + "']/../.."))
                    .stream()
                    .peek(element -> log.info("Found nano card: {}", element.getText()))
                    .findAny()
                    .map(nanoCard -> nanoCard.findElements(By.xpath("a[contains(@class, 'price-link-login') and text()='Заказать']")))
                    .flatMap(buttons -> buttons.stream().findAny())
                    .ifPresentOrElse(
                            button -> log.info("Found active order button"),
                            () -> log.info("No active order button found")
                    );
        } catch (Exception ex) {
            log.error("Error during RuWeb VPS Nano check", ex);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
