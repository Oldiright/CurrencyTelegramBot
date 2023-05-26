package telegram.settings;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.UserSettings;
import telegram.settings.settingsItems.AlertTimesSettings;
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

            ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();

            for(int i = 0; i < callbackQueryData.length; i++) {
                ArrayList<InlineKeyboardButton> settingsItem = Utils.createButtonForColumnsKeyboard(callbackQueryText[i], callbackQueryData[i]);
                buttons.add(settingsItem);
            }

            InlineKeyboardMarkup keyboard = Utils.createColumnsKeyboard(buttons);
            message.setReplyMarkup(keyboard);

            return message;

        } else if(update.getCallbackQuery().getData().contains(CALLBACK_QUERY_DATA_SETTINGS[0])) {

            //Кількість знаків після коми
            return NumberOfDecimalPlaces.settingsNumberOfDecimalPlacesMessage(update, chatId,userSettings);

        } else if(update.getCallbackQuery().getData().contains(CALLBACK_QUERY_DATA_SETTINGS[1])) {

            //Банк
            return SendMessage.builder().text("TEST").build();  /* заглушка */

        } else if(update.getCallbackQuery().getData().contains(CALLBACK_QUERY_DATA_SETTINGS[2])) {

            //Валюти

            return SendMessage.builder().text("TEST").build();  /* заглушка */


        } else if(update.getCallbackQuery().getData().contains(CALLBACK_QUERY_DATA_SETTINGS[3])) {

            //Час оповіщень
            return AlertTimesSettings.settingsAlertTimeAlertTimesMessage(update,chatId,userSettings);
            //return SendMessage.builder().text("TEST").build(); /* заглушка */
        }

        return SendMessage.builder().text("TEST").build();  /* заглушка */

    }

    public static SendMessage messageHandler(Update update, long chatId, UserSettings user){
        SendMessage message = AlertTimesSettings.settingsAlertTimeAlertTimesMessage(update, chatId, user);
        String text = "Ви ввели не коректні данні, для збереження коректних налаштувань, скористайтесь клавіатурою нижче:";
        String inputMessage = update.getMessage().getText();

        int isInt;
        try {
            isInt = Integer.parseInt(inputMessage);
            if (isInt < 9 || isInt > 18){
                text = "Нажаль в обраний вами час банки не працюють. Оберіть час з клавіатури нижче:";
                message.setText(text);
                return message;
            }
        }catch (Exception e){
            message.setText(text);
            return message;
        }

        user.setAlertTimes(inputMessage);
        text = "Час сповіщень встановлено на: " + user.getAlertTimes() + ":00";
        message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);
        return message;

    }

}
