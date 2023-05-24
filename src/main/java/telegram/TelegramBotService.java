package telegram;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBotService {
    private final CurrencyTelegramBot currencyTelegramBot;
    public TelegramBotService(){
        currencyTelegramBot = new CurrencyTelegramBot();

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(currencyTelegramBot);
        }catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }
}
