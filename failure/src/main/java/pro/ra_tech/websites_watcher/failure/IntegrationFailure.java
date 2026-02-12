package pro.ra_tech.websites_watcher.failure;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;

public class IntegrationFailure extends AbstractFailure<IntegrationFailure.Code> {
    private static final String DETAIL = "Integration service API call error";

    public IntegrationFailure(Code code, String source, @Nullable String message) {
        super(code, DETAIL, source, message);
    }

    public IntegrationFailure(Code code, String source, Throwable cause) {
        super(code, DETAIL, source, cause);
    }

    @RequiredArgsConstructor
    public enum Code {
        TELEGRAM_BOT_INTEGRATION_FAILURE("TELEGRAM_BOT_INTEGRATION_FAILURE");

        private final String value;

        @Override
        public String toString() {
            return value;
        }
    }
}
