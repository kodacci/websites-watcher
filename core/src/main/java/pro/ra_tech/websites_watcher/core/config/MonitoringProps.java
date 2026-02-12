package pro.ra_tech.websites_watcher.core.config;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.monitoring")
public record MonitoringProps(
        @NotEmpty
        String appName,
        @NotEmpty
        String appVersion,
        @NotEmpty
        String podName,
        @NotEmpty
        String podNamespace,
        @NotEmpty
        String nodeName
) {
}
