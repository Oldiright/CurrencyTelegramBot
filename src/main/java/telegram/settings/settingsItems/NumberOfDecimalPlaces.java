package telegram.settings.settingsItems;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.UserSettings;
import telegram.settings.utils.Utils;
import java.util.ArrayList;

public class NumberOfDecimalPlaces {

    public static final String TITLE ="Settings_Number_of_decimal_places";
    private static final String[] CALLBACK_QUERY_DATA_SETTINGS_NUMBER_OF_DECIMAL_PLACES =new String[]{"Settings_Number_of_decimal_places_2","Settings_Number_of_decimal_places_3", "Settings_Number_of_decimal_places_4", "Settings"};
    private static final String FIRST_MESSAGE_TEXT ="Оберіть кількість знаків після коми:";
    private static final String[] BUTTON_TEXT_SETTINGS_NUMBER_OF_DECIMAL_PLACES = new String[]{"2", "3", "4", "До меню налаштувань"};


    public static SendMessage settingsNumberOfDecimalPlacesMessage(Update update, Long chatId, UserSettings userSettings) {


        if((update.getCallbackQuery().getData().equals(TITLE))) {

            return NumberOfDecimalPlacesMessage(chatId, userSettings);

        } else if((update.getCallbackQuery().getData().equals(CALLBACK_QUERY_DATA_SETTINGS_NUMBER_OF_DECIMAL_PLACES[0]))) {

            userSettings.setNumberOfDecimalPlaces(2);

            return NumberOfDecimalPlacesMessage(chatId, userSettings);

        } else if((update.getCallbackQuery().getData().equals(CALLBACK_QUERY_DATA_SETTINGS_NUMBER_OF_DECIMAL_PLACES[1]))) {

            userSettings.setNumberOfDecimalPlaces(3);

            return NumberOfDecimalPlacesMessage(chatId, userSettings);

        } else if((update.getCallbackQuery().getData().equals(CALLBACK_QUERY_DATA_SETTINGS_NUMBER_OF_DECIMAL_PLACES[2]))) {

            userSettings.setNumberOfDecimalPlaces(4);

            return NumberOfDecimalPlacesMessage(chatId, userSettings);

        } else {

            return NumberOfDecimalPlacesMessage(chatId, userSettings);
        }

    }

    public static ArrayList<ArrayList<InlineKeyboardButton>> getButtonsForSettingsNumberOfDecimalPlaces(UserSettings userSettings) {

        String[] buttonText = BUTTON_TEXT_SETTINGS_NUMBER_OF_DECIMAL_PLACES.clone();
        String[] buttonCallbackData = CALLBACK_QUERY_DATA_SETTINGS_NUMBER_OF_DECIMAL_PLACES.clone();

        buttonText[userSettings.getNumberOfDecimalPlaces() - 2] = "✔️" + userSettings.getNumberOfDecimalPlaces();

        ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();

        for(int i = 0; i < buttonText.length; i++) {
            ArrayList<InlineKeyboardButton> NumberOfDecimalPlaces = Utils.createButtonForColumnsKeyboard(buttonText[i], buttonCallbackData[i]);
            buttons.add(NumberOfDecimalPlaces);
        }

        return buttons;
    }


    private static SendMessage NumberOfDecimalPlacesMessage(Long chatId, UserSettings userSettings) {

        SendMessage message = Utils.createMessage(FIRST_MESSAGE_TEXT, chatId);

        InlineKeyboardMarkup keyboard = Utils.createColumnsKeyboard(getButtonsForSettingsNumberOfDecimalPlaces(userSettings));

        message.setReplyMarkup(keyboard);
        return message;
    }
}
