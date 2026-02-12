package pro.ra_tech.websites_watcher.core.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.micrometer.metrics.autoconfigure.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import pro.ra_tech.websites_watcher.core.schedule.RuWebVpsWatcher;
import pro.ra_tech.websites_watcher.core.webdriver.api.WebdriverManager;
import pro.ra_tech.websites_watcher.core.webdriver.impl.WebdriverManagerImpl;
import pro.ra_tech.websites_watcher.integration.api.TelegramBotService;
import pro.ra_tech.websites_watcher.integration.config.IntegrationConfiguration;

@Slf4j
@Configuration
@EnableScheduling
@ComponentScan({
        "pro.ra_tech.websites_watcher.core.schedule"
})
@EnableConfigurationProperties({
        RuWebWatcherProps.class,
        ApplicationProps.class,
        MonitoringProps.class
})
@Import(IntegrationConfiguration.class)
public class MainConfiguration {
    @Bean
    public WebdriverManager webdriverManager(ApplicationProps props, TelegramBotService telegramBotService) {
        return new WebdriverManagerImpl(props.remoteChromeSeleniumUrl());
    }

    @Bean
    public RuWebVpsWatcher ruWebVpsWatcher(
            RuWebWatcherProps props,
            WebdriverManager webdriverManager,
            TelegramBotService telegramBotService
    ) {
        return new RuWebVpsWatcher(
                props.url(),
                props.planName(),
                webdriverManager,
                telegramBotService,
                props.sendNegativeNotifications()
        );
    }

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> registryCustomizer(MonitoringProps props) {
        log.info("Loaded application monitoring props: {}", props);

        return registry -> registry.config().commonTags(
                "app.name", props.appName(),
                "app.version", props.appVersion(),
                "pod.name", props.podName(),
                "pod.namespace", props.podNamespace(),
                "pod.node.name", props.nodeName()
        );
    }
}
