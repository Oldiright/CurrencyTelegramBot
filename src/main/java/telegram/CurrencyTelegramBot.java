package telegram;


import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.commands.GetCommand;
import telegram.commands.InfoCommand;
import telegram.commands.StartCommand;
import telegram.scheduler.AlertScheduler;
import telegram.settings.Settings;
import telegram.settings.utils.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

    AlertScheduler alertScheduler;
    private final static Map<Long, UserSettings> userSettings;

    static {
        try {
            userSettings = Utils.getUserSettingsData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    CurrencyTelegramBot(AlertScheduler alertScheduler) {
        this.alertScheduler = alertScheduler;

        register(new StartCommand());
        register(new GetCommand());
        register(new InfoCommand());

    }

    public static Map<Long, UserSettings> getUserSettings() {
        return userSettings;
    }

    @Override
    public String getBotUsername() {

        return new BotConstants().botInit().getBotName();
    }

    @Override
    public String getBotToken() {

        return new BotConstants().botInit().getBotToken();
    }


    @Override
    public void processNonCommandUpdate(Update update) {




        Long chatId = Utils.getChatId(update);
        if(!userSettings.containsKey(chatId)) {
            userSettings.put(chatId, new UserSettings());
            try {
                Utils.writerInTheBase(userSettings);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        if(update.hasCallbackQuery()) {
            System.out.println(chatId);
            System.out.println(getCallbackQueryData(update));

            if (getCallbackQueryData(update).contains("Settings")) {


                sendApiMethodAsync(Settings.settingsMessage(update, chatId, userSettings.get(chatId)));



            }

            if (getCallbackQueryData(update).contains("Get Info")) {

                // отримання інформації

                SendMessage sendMessage = GetInfo.infoMessage(update, chatId, userSettings.get(chatId));

                sendApiMethodAsync(sendMessage);

            }

            if (getCallbackQueryData(update).equals("To Start")) {
                SendMessage sendMessage = SendMessage
                        .builder()
                        .chatId(chatId)
                        .text("Головне меню")
                        .replyMarkup(Utils.getGeneralMenuKeyboard())
                        .build();
                sendApiMethodAsync(sendMessage);

            }

        }


        if (update.hasMessage()) {
            sendApiMethodAsync(Settings.messageHandler(update, update.getMessage().getFrom().getId(), userSettings.get(chatId), alertScheduler));

            //повідомлення з головним меню
            sendApiMethodAsync(SendMessage
                    .builder()
                    .chatId(chatId)
                    .text("Головне меню")
                    .replyMarkup(Utils.getGeneralMenuKeyboard())
                    .build());


        }

    }

    public void saveData(){
        try{
            Utils.writerInTheBase(userSettings);
            Utils.writerInTheBase(alertScheduler);
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public String getCallbackQueryData(Update update) {
        return update.getCallbackQuery().getData();
    }




}
