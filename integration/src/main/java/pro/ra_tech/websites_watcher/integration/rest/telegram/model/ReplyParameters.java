package pro.ra_tech.websites_watcher.integration.rest.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReplyParameters(
        @JsonProperty("message_id") int messageId
) {
}
