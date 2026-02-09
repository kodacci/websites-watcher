package pro.ra_tech.websites_watcher.core.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.common")
public record ApplicationProps(
        @NotEmpty
        String chromeDriverPath
) {
}
