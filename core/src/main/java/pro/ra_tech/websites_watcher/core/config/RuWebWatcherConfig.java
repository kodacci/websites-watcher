package pro.ra_tech.websites_watcher.core.config;

import jakarta.validation.constraints.NotEmpty;

public record RuWebWatcherConfig(
        @NotEmpty
        String cron,
        @NotEmpty
        String url,
        @NotEmpty
        String planName
) {
}
