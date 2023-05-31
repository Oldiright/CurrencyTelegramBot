package telegram.settings;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.UserSettings;
import telegram.scheduler.AlertScheduler;
import telegram.settings.settingsItems.AlertTimesSettings;
import telegram.settings.settingsItems.BankSettings;
import telegram.settings.settingsItems.CurrencySettings;
import telegram.settings.settingsItems.NumberOfDecimalPlaces;
import telegram.settings.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Getter

public class Settings {
    public static final String TITLE ="Settings";
    private static final String[] CALLBACK_QUERY_DATA_SETTINGS =new String[]{"Settings_Bank", "Settings_Currency", "Settings_Alert_times", "Settings_Number_of_decimal_places", "To Start"};
    private static final String FIRST_MESSAGE_TEXT ="Налаштування";
    private static final String[] CALLBACK_QUERY_TEXT_SETTINGS = new String[]{"Банк", "Валюти", "Час оповіщень", "Кількість знаків після коми", "До головного меню"};
    public static final String INVALID_TEXT_MESSAGE = "Ви ввели не коректні данні, якщо ви налаштовуєте регулярні сповіщення скористайтеся меню налаштувань:";
    public static final String INVALID_TIME_IN_MESSAGE = "Якщо ви зараз намагаєтесь налаштувати час сповіщень, зауважимо, що доступні години для налаштування з 09:00 до 18:00. Скористайтеся меню налаштувань для подальшої кастомізації";
    public static final String TORN_OFF_NOTIFICATION = "Автоматичну розсилку сповіщень вимкнемо. Ви завжди можете змінити це в налаштуваннях.";
    public static final String PART_OF_VALID_MESSAGE = "Час сповіщень встановлено на:";

    public static BotApiMethod settingsMessage(Update update, Long chatId, UserSettings userSettings) {

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

            //Банк
            return BankSettings.settingsBankMessage(update, chatId, userSettings);

        } else if(update.getCallbackQuery().getData().contains(CALLBACK_QUERY_DATA_SETTINGS[1])) {


            //Валюти

            return CurrencySettings.settingsCurrencyMessage(update, chatId, userSettings);

        } else if(update.getCallbackQuery().getData().contains(CALLBACK_QUERY_DATA_SETTINGS[2])) {

            //Час оповіщень
            return AlertTimesSettings.settingsAlertTimeAlertTimesMessage(update,chatId,userSettings);



        } else if(update.getCallbackQuery().getData().contains(CALLBACK_QUERY_DATA_SETTINGS[3])) {


            //Кількість знаків після коми
            return NumberOfDecimalPlaces.settingsNumberOfDecimalPlacesMessage(update, chatId,userSettings);


        }

        return SendMessage.builder().text("TEST").build();  /* заглушка */

    }

    public static SendMessage messageHandler(Update update, Long chatId, UserSettings user, AlertScheduler alertScheduler){

        String inputMessage = update.getMessage().getText();

        // перевірка змісту повідомлення

        if(inputMessage.equals("Вимкнути сповіщення")) {

            user.setNeedAlertTimes(false);

            //видалення користувача з планувальника сповіщень;

            removeUserFromAlertScheduler(alertScheduler,  chatId);

            return SendMessage.builder()
                    .text(TORN_OFF_NOTIFICATION)
                    .chatId(chatId)
                    .replyMarkup(ReplyKeyboardRemove.builder()
                            .removeKeyboard(true)
                            .build())
                    .build();

        }

        boolean isDigit = true;

        for(int i = 0; i < inputMessage.length(); i++) {

            if(!Character.isDigit(inputMessage.charAt(i))) {
                isDigit = false;
                break;
            }
        }

        if(!isDigit || inputMessage.charAt(0) == '0') {

            return SendMessage.builder()
                    .chatId(chatId)
                    .text(INVALID_TEXT_MESSAGE)
                    .replyMarkup(ReplyKeyboardRemove
                            .builder()
                            .removeKeyboard(true)
                            .build())
                    .build();
        }

        // обробка вказаного часу

        int desiredTime = Integer.parseInt(inputMessage);

        if(desiredTime < 9 || desiredTime > 18) {

            return SendMessage.builder()
                    .chatId(chatId)
                    .text(INVALID_TIME_IN_MESSAGE)
                    .replyMarkup(ReplyKeyboardRemove
                            .builder()
                            .removeKeyboard(true)
                            .build())
                    .build();
        } else {

            if(inputMessage.length() == 1) {
                user.setAlertTimes("0" + inputMessage);
            } else {
                user.setAlertTimes(inputMessage);
            }

            user.setNeedAlertTimes(true);

            removeUserFromAlertScheduler(alertScheduler, chatId);

                    // додавання користувача до розкладу сповіщень

            if(!alertScheduler.getScheduler().containsKey(inputMessage)) {
                alertScheduler.getScheduler().put(inputMessage, new ConcurrentHashMap<>());
            }
            alertScheduler.getScheduler().get(inputMessage).put(chatId, user);


            return SendMessage.builder()
                    .chatId(chatId)
                    .text("Час сповіщень встановлено на: " + user.getAlertTimes() + ":00")
                    .replyMarkup(ReplyKeyboardRemove
                            .builder()
                            .removeKeyboard(true)
                            .build())
                    .build();
        }

    }

    public static void removeUserFromAlertScheduler(AlertScheduler alertScheduler, Long chatId) {
        for(int i = 9; i <= 18; ++i) {
            if(alertScheduler.getScheduler().containsKey(Integer.toString(i))) {
                alertScheduler.getScheduler().get(Integer.toString(i)).remove(chatId);
            }
        }
    }
}