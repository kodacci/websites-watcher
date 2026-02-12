package pro.ra_tech.websites_watcher.integration.config;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.telegram.api")
public record TelegramProps(
        @NotEmpty
        String restApiBaseUrl,
        @NotEmpty
        String apiToken,
        @Positive
        int requestTimeoutMs,
        @Min(0)
        int maxRetries,
        long notificationsChatId
) {
}
