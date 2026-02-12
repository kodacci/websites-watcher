package pro.ra_tech.websites_watcher.integration.config;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.val;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pro.ra_tech.websites_watcher.integration.api.TelegramBotService;
import pro.ra_tech.websites_watcher.integration.impl.TelegramBotServiceImpl;
import pro.ra_tech.websites_watcher.integration.rest.telegram.api.TelegramBotApi;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(TelegramProps.class)
public class IntegrationConfiguration extends BaseIntegrationConfig {
    private static final String TELEGRAM_SERVICE = "telegram";
    private static final String SEND_MESSAGE_METHOD = "send-message";
    private static final String GET_ME_METHOD = "get-me";

    private TelegramBotApi telegramApi(
            TelegramProps props
    ) {
        val client = new OkHttpClient.Builder()
                .callTimeout(props.requestTimeoutMs(), TimeUnit.MILLISECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(props.restApiBaseUrl() + "/bot" + props.apiToken() + "/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build()
                .create(TelegramBotApi.class);
    }

    @Bean
    public TelegramBotService telegramBotService(MeterRegistry registry, TelegramProps props) {
        return new TelegramBotServiceImpl(
                telegramApi(props),
                props.notificationsChatId(),
                props.maxRetries(),
                buildTimer(registry, TELEGRAM_SERVICE, SEND_MESSAGE_METHOD),
                buildTimer(registry, TELEGRAM_SERVICE, GET_ME_METHOD),
                buildCounter(registry, ErrorCounterType.STATUS_4XX, TELEGRAM_SERVICE, SEND_MESSAGE_METHOD),
                buildCounter(registry, ErrorCounterType.STATUS_4XX, TELEGRAM_SERVICE, GET_ME_METHOD),
                buildCounter(registry, ErrorCounterType.STATUS_5XX, TELEGRAM_SERVICE, SEND_MESSAGE_METHOD),
                buildCounter(registry, ErrorCounterType.STATUS_5XX, TELEGRAM_SERVICE, GET_ME_METHOD)
        );
    }
}
