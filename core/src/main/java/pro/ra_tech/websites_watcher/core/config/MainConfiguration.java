package pro.ra_tech.websites_watcher.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({
        "pro.ra_tech.websites_watcher.core.schedule"
})
public class MainConfiguration {
}
