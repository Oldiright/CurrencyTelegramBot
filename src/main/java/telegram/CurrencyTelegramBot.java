package telegram;


import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.commands.StartCommand;
import telegram.settings.Settings;
import telegram.settings.utils.Utils;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

    UserSettings userSettings = new UserSettings();

    CurrencyTelegramBot() {
        register(new StartCommand());

    }

    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {

        return BotConstants.BOT_TOKEN;
    }


    @Override
    public void processNonCommandUpdate(Update update) {

        Long chatId = Utils.getChatId(update);
        System.out.println(chatId);


        if(update.hasCallbackQuery()) {
            System.out.println(getCallbackQueryData(update));

            if(getCallbackQueryData(update).contains("Settings")) {

                SendMessage sendMessage = Settings.settingsMessage(update, chatId, userSettings);

                sendApiMethodAsync(sendMessage);

            }
        }



    }

    public String getCallbackQueryData(Update update) {
        return update.getCallbackQuery().getData();
    }




}
