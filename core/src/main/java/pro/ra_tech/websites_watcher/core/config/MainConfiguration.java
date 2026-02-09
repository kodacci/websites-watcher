package pro.ra_tech.websites_watcher.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import pro.ra_tech.websites_watcher.core.schedule.RuWebVpsWatcher;
import pro.ra_tech.websites_watcher.core.webdriver.api.WebdriverManager;
import pro.ra_tech.websites_watcher.core.webdriver.impl.WebdriverManagerImpl;

@Configuration
@EnableScheduling
@ComponentScan({
        "pro.ra_tech.websites_watcher.core.schedule"
})
@EnableConfigurationProperties({RuWebWatcherProps.class, ApplicationProps.class})
public class MainConfiguration {
    @Bean
    public WebdriverManager webdriverManager(ApplicationProps props) {
        return new WebdriverManagerImpl(props.remoteChromeSeleniumUrl());
    }

    @Bean
    public RuWebVpsWatcher ruWebVpsWatcher(RuWebWatcherProps props, WebdriverManager webdriverManager) {
        return new RuWebVpsWatcher(props.url(), props.planName(), webdriverManager);
    }
}
