package telegram;


import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.commands.StartCommand;
import telegram.settings.Settings;
import telegram.settings.utils.Utils;

import java.util.HashMap;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {


    HashMap<Long, UserSettings> userSettings = new HashMap<>();

    CurrencyTelegramBot() {
        register(new StartCommand());

    }

    @Override
    public String getBotUsername() {
//        return BotConstants.BOT_NAME;
        return new BotConstants().botInit().getBotName();
    }

    @Override
    public String getBotToken() {
//        return BotConstants.BOT_TOKEN;
        return new BotConstants().botInit().getBotToken();
    }


    @Override
    public void processNonCommandUpdate(Update update) {


        Long chatId = Utils.getChatId(update);
        if(!userSettings.containsKey(chatId)) {
            userSettings.put(chatId, new UserSettings());
            userSettings.get(chatId).setChatId(chatId);
        }

        if(update.hasCallbackQuery()) {
            System.out.println(chatId);
            System.out.println(getCallbackQueryData(update));

            if(getCallbackQueryData(update).contains("Settings")) {

                SendMessage sendMessage = Settings.settingsMessage(update, chatId, userSettings.get(chatId));

                sendApiMethodAsync(sendMessage);

            }

            if(getCallbackQueryData(update).contains("Get Info")) {

                // отримання інформації

                SendMessage sendMessage = GetInfo.infoMessage(update, chatId, userSettings.get(chatId));

                sendApiMethodAsync(sendMessage);


            }

        }

        if (update.hasMessage()){
            sendApiMethodAsync(Settings.messageHandler(update, update.getMessage().getFrom().getId(), userSettings.get(chatId)));
        }





    }

    public String getCallbackQueryData(Update update) {
        return update.getCallbackQuery().getData();
    }




}
