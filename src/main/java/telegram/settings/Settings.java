package telegram.settings;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.UserSettings;
import telegram.settings.settingsItems.NumberOfDecimalPlaces;
import telegram.settings.utils.Utils;
import java.util.ArrayList;

public class Settings {

    public static final String TITLE ="Settings";
    private static final String[] CALLBACK_QUERY_DATA_SETTINGS =new String[]{"Settings_Number_of_decimal_places","Settings_Bank", "Settings_Currency", "Settings_Alert_times"};
     private static final String FIRST_MESSAGE_TEXT ="Налаштування";
    private static final String[] CALLBACK_QUERY_TEXT_SETTINGS = new String[]{"Кількість знаків після коми", "Банк", "Валюти", "Час оповіщень"};


    public static SendMessage settingsMessage(Update update, Long chatId, UserSettings userSettings) {

        if(update.getCallbackQuery().getData().equals(TITLE)) {
            SendMessage message = Utils.createMessage(FIRST_MESSAGE_TEXT, chatId);
            String[] callbackQueryText = CALLBACK_QUERY_TEXT_SETTINGS.clone();
            String[] callbackQueryData = CALLBACK_QUERY_DATA_SETTINGS.clone();

            ArrayList<InlineKeyboardButton> numberOfDecimalPlaces = Utils.createButtonForColumnsKeyboard(callbackQueryText[0], callbackQueryData[0]);

            ArrayList<InlineKeyboardButton> bank = Utils.createButtonForColumnsKeyboard(callbackQueryText[1], callbackQueryData[1]);

            ArrayList<InlineKeyboardButton> currency = Utils.createButtonForColumnsKeyboard(callbackQueryText[2], callbackQueryData[2]);

            ArrayList<InlineKeyboardButton> alertTimes = Utils.createButtonForColumnsKeyboard(callbackQueryText[3], callbackQueryData[3]);

            ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(numberOfDecimalPlaces);
            buttons.add(bank);
            buttons.add(currency);
            buttons.add(alertTimes);
            InlineKeyboardMarkup keyboard;
            keyboard = Utils.createColumnsKeyboard(buttons);
            message.setReplyMarkup(keyboard);

            return message;

        } else if(update.getCallbackQuery().getData().contains("Settings_Number_of_decimal_places")) {

            return NumberOfDecimalPlaces.settingsNumberOfDecimalPlacesMessage(update, chatId,userSettings);

        } else {
            return SendMessage.builder().text("TEST").build();
        }

    }

}
