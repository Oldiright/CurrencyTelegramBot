package telegram.settings.settingsItems;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.UserSettings;
import telegram.settings.utils.Utils;

import java.util.ArrayList;

public class CurrencySettings {

    public static final String[] BUTTON_TEXT_SETTINGS_CURRENCY = new String[]{"USD", "EUR", "До меню налаштувань"};
    public static final String[] CALLBACK_QUERY_DATA_SETTINGS_CURRENCY = new String[]{"Settings_Currency_USD", "Settings_Currency_EUR", "Settings"};
    private static final String FIRST_MESSAGE_TEXT ="Оберіть валюти:";




    public static SendMessage settingsCurrencyMessage(Update update, Long chatId, UserSettings userSettings) {


        if(getCallbackQueryData(update).equals("Settings_Currency")) {
            SendMessage message = Utils.createMessage(FIRST_MESSAGE_TEXT, chatId);
            InlineKeyboardMarkup keyboard = Utils.createColumnsKeyboard(getButtonsForSettingsCurrency(userSettings));
            message.setReplyMarkup(keyboard);
            return message;
        } else if(getCallbackQueryData(update).equals("Settings_Currency_USD")) {
            userSettings.setUsd(!userSettings.isUsd());
            if(!userSettings.isEuro()) {userSettings.setEuro(true);}
            SendMessage message = Utils.createMessage(FIRST_MESSAGE_TEXT, chatId);
            InlineKeyboardMarkup keyboard = Utils.createColumnsKeyboard(getButtonsForSettingsCurrency(userSettings));
            message.setReplyMarkup(keyboard);
            return message;
        } else if(getCallbackQueryData(update).equals("Settings_Currency_EUR")) {
            userSettings.setEuro(!userSettings.isEuro());
            if(!userSettings.isUsd()) {userSettings.setUsd(true);}
            SendMessage message = Utils.createMessage(FIRST_MESSAGE_TEXT, chatId);
            InlineKeyboardMarkup keyboard = Utils.createColumnsKeyboard(getButtonsForSettingsCurrency(userSettings));
            message.setReplyMarkup(keyboard);
            return message;

        } else

        return SendMessage.builder().text("Error in Currency settings").build();
    }
    public static String getCallbackQueryData(Update update) {
        return update.getCallbackQuery().getData();
    }

    public static ArrayList<ArrayList<InlineKeyboardButton>> getButtonsForSettingsCurrency(UserSettings userSettings) {
        String[] buttonText = BUTTON_TEXT_SETTINGS_CURRENCY.clone();
        if(userSettings.isUsd()) {
            buttonText[0] = "✔️" + buttonText[0];
        }
        if(userSettings.isEuro()) {
            buttonText[1] = "✔️" + buttonText[1];
        }
        ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
        for(int i = 0; i < buttonText.length; i++) {
            ArrayList<InlineKeyboardButton> button = Utils.createButtonForColumnsKeyboard(buttonText[i], CALLBACK_QUERY_DATA_SETTINGS_CURRENCY[i]);
            buttons.add(button);
        }
        return buttons;
    }


}
