package pro.ra_tech.websites_watcher.core.schedule;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.ra_tech.websites_watcher.core.schedule.api.WebSiteWatcher;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RuWebVpsWatcher implements WebSiteWatcher {
    private static final String RU_WEB_URL = "https://ruweb.net/vps-europe/netherlands";

    @Override
    @Scheduled(initialDelay = 3, fixedDelay = Integer.MAX_VALUE, timeUnit = TimeUnit.SECONDS)
    public void check() {
            log.info("Checking RuWeb VPS Nano in Netherlands");

            val options =  new ChromeOptions();
            options.addArguments("--headless=new", "--window-size=1920,1080");
            val driver = new ChromeDriver(options);

        try {
            driver.get(RU_WEB_URL);

            driver.findElements(By.xpath("//p[contains(@class, 'tariff-card__title') and text()='KVMe-NANO']/../.."))
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
            driver.quit();
        }
    }
}
