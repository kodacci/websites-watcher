package pro.ra_tech.websites_watcher.core.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.watchers.ru-web")
public record RuWebWatcherProps(
        @NotEmpty
        String cron,
        @NotEmpty
        String url,
        @NotEmpty
        String planName,
        boolean sendNegativeNotifications
) {
}
