package pro.ra_tech.websites_watcher.integration.rest.telegram.api;

import pro.ra_tech.websites_watcher.integration.rest.telegram.model.SendMessageRequest;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.TelegramApiResponse;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.TelegramMessage;
import pro.ra_tech.websites_watcher.integration.rest.telegram.model.TelegramUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface TelegramBotApi {

    @POST("getMe")
    @Headers("Content-type: application/json")
    Call<TelegramApiResponse<TelegramUser>> getMe();

    @POST("sendMessage")
    @Headers("Content-type: application/json")
    Call<TelegramApiResponse<TelegramMessage>> sendMessage(@Body SendMessageRequest request);
}
