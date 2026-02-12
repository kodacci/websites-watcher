package pro.ra_tech.websites_watcher.integration.impl;

import dev.failsafe.RetryPolicy;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.jspecify.annotations.Nullable;
import pro.ra_tech.websites_watcher.failure.AppFailure;
import pro.ra_tech.websites_watcher.failure.IntegrationFailure;
import pro.ra_tech.websites_watcher.integration.api.TelegramBotService;
import pro.ra_tech.websites_watcher.integration.rest.telegram.api.TelegramBotApi;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.MessageParseMode;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.SendMessageRequest;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.TelegramApiResponse;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.TelegramMessage;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.TelegramUser;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Optional;

@Slf4j
public class TelegramBotServiceImpl extends BaseRestService implements TelegramBotService {
    private final TelegramBotApi api;
    private final long chatId;
    private final Timer sendMessageTimer;
    private final Timer getMeTimer;
    private final Counter sendMessage4xxCounter;
    private final Counter sendMessage5xxCounter;
    private final Counter getMe4xxCounter;
    private final Counter getMe5xxCounter;
    private final RetryPolicy<Response<TelegramApiResponse<TelegramUser>>> getMePolicy;
    private final RetryPolicy<Response<TelegramApiResponse<TelegramMessage>>> sendMessagePolicy;

    public TelegramBotServiceImpl(
            TelegramBotApi api,
            long chatId,
            int maxRetries,
            Timer sendMessageTimer,
            Timer getMeTimer,
            Counter sendMessage4xxCounter,
            Counter getMe4xxCounter,
            Counter sendMessage5xxCounter,
            Counter getMe5xxCounter
    ) {
        this.api = api;
        this.chatId = chatId;
        this.sendMessageTimer = sendMessageTimer;
        this.getMeTimer = getMeTimer;
        this.sendMessage4xxCounter = sendMessage4xxCounter;
        this.sendMessage5xxCounter = sendMessage5xxCounter;
        this.getMe4xxCounter = getMe4xxCounter;
        this.getMe5xxCounter = getMe5xxCounter;

        sendMessagePolicy = buildPolicy(maxRetries);
        getMePolicy = buildPolicy(maxRetries);
    }

    private AppFailure toFailure(Throwable cause) {
        return toFailure(IntegrationFailure.Code.TELEGRAM_BOT_INTEGRATION_FAILURE, getClass().getName(), cause);
    }

    private <T> Either<AppFailure, T> sendTelegramRequest(
            RetryPolicy<Response<TelegramApiResponse<T>>> policy,
            Call<TelegramApiResponse<T>> call,
            @Nullable Timer timer,
            Counter status4xxCounter,
            Counter status5xxCounter
    ) {
        return Optional.ofNullable(timer)
                .map(notNull ->
                        sendMeteredRequest(policy, notNull, status4xxCounter, status5xxCounter, call, this::toFailure)
                )
                .orElseGet(() -> sendRequest(policy, call, this::toFailure))
                .flatMap(res -> {
                    log.debug("Telegram response: {}", res);
                    if (res.ok() && res.result() != null) {
                        return Either.right(res.result());
                    }

                    return Either.left(new IntegrationFailure(
                            IntegrationFailure.Code.TELEGRAM_BOT_INTEGRATION_FAILURE,
                            getClass().getName(),
                            res.ok() ? "Empty response" : res.error()
                    ));
                });
    }

    @Override
    public Either<AppFailure, TelegramUser> getMe() {
        return sendTelegramRequest(getMePolicy, api.getMe(), getMeTimer, getMe4xxCounter, getMe5xxCounter);
    }

    @Override
    public Either<AppFailure, TelegramMessage> sendMessage(String text) {
        return sendMessage(text, null);
    }

    @Override
    public Either<AppFailure, TelegramMessage> sendMessage(String text, MessageParseMode parseMode) {
        val request = new SendMessageRequest(chatId, text, parseMode,false, null);

        return sendTelegramRequest(
                sendMessagePolicy,
                api.sendMessage(request),
                sendMessageTimer,
                sendMessage4xxCounter,
                sendMessage5xxCounter
        );
    }
}
