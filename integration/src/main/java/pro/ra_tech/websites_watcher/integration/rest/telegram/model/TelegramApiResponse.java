package pro.ra_tech.websites_watcher.integration.rest.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jspecify.annotations.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TelegramApiResponse<T> (
        @JsonProperty("ok") boolean ok,
        @JsonProperty("description") String description,
        @JsonProperty("result") @Nullable T result,
        @JsonProperty("error") @Nullable String error
) {
}
