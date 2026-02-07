package pro.ra_tech.websites_watcher.core.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import pro.ra_tech.websites_watcher.core.schedule.RuWebVpsWatcher;

@Configuration
@EnableScheduling
@ComponentScan({
        "pro.ra_tech.websites_watcher.core.schedule"
})
@EnableConfigurationProperties(RuWebWatcherProps.class)
public class MainConfiguration {
    @Bean
    public RuWebVpsWatcher ruWebVpsWatcher(RuWebWatcherProps props) {
        return new RuWebVpsWatcher(props.url(), props.planName());
    }
}
