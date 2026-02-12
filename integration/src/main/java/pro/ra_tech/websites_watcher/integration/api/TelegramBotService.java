package pro.ra_tech.websites_watcher.integration.api;

import io.vavr.control.Either;
import pro.ra_tech.websites_watcher.failure.AppFailure;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.MessageParseMode;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.TelegramMessage;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.TelegramUser;

public interface TelegramBotService {
    Either<AppFailure, TelegramUser> getMe();
    Either<AppFailure, TelegramMessage> sendMessage(String text);
    Either<AppFailure, TelegramMessage> sendMessage(String text, MessageParseMode parseMode);
}
