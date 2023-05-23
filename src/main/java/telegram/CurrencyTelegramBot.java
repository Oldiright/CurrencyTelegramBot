package telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.commands.StartCommand;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

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
        UserSettings userSettings = new UserSettings();

        Long chaId = getChatId(update);
        System.out.println(chaId);


        if(update.hasCallbackQuery()) {
            System.out.println(getCallbackQueryData(update));
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings")) {

            String text = "Налаштування:";

            SendMessage message = createMassage(text, chaId);

            ArrayList<InlineKeyboardButton> numberOfDecimalPlaces = createButton("Кількість знаків після коми", "Settings_Number_of_decimal_places");

            ArrayList<InlineKeyboardButton> bank = createButton("Банк", "Settings_Bank");

            ArrayList<InlineKeyboardButton>  currency = createButton("Валюти", "Settings_Currency");

            ArrayList<InlineKeyboardButton>  alertTimes = createButton("Час оповіщень", "Settings_Alert_times");

            ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(numberOfDecimalPlaces);
            buttons.add(bank);
            buttons.add(currency);
            buttons.add(alertTimes);

            InlineKeyboardMarkup keyboard = createKeyboard(buttons);

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);

        }
        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Number_of_decimal_places")) {

            String text = "Оберіть кількість знаків після коми:";

            SendMessage message = createMassage(text, chaId);

            ArrayList<InlineKeyboardButton> twoNumberOfDecimalPlaces;
            ArrayList<InlineKeyboardButton> threeNumberOfDecimalPlaces;
            ArrayList<InlineKeyboardButton> fourNumberOfDecimalPlaces;

            String[] buttonText = new String[]{"2", "3", "4"};
            buttonText[userSettings.getNumberOfDecimalPlaces() - 2] = "✔️" + userSettings.getNumberOfDecimalPlaces();

            twoNumberOfDecimalPlaces = createButton(buttonText[0], "Settings_Number_of_decimal_places_2");

            threeNumberOfDecimalPlaces = createButton(buttonText[1], "Settings_Number_of_decimal_places_3");

            fourNumberOfDecimalPlaces = createButton(buttonText[2], "Settings_Number_of_decimal_places_4");

            ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(twoNumberOfDecimalPlaces);
            buttons.add(threeNumberOfDecimalPlaces);
            buttons.add(fourNumberOfDecimalPlaces);

            InlineKeyboardMarkup keyboard = createKeyboard(buttons);

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Number_of_decimal_places_2")) {

            userSettings.setNumberOfDecimalPlaces(2);
            String text = "Оберіть кількість знаків після коми:";

            SendMessage message = createMassage(text, chaId);

            ArrayList<InlineKeyboardButton> twoNumberOfDecimalPlaces;
            ArrayList<InlineKeyboardButton> threeNumberOfDecimalPlaces;
            ArrayList<InlineKeyboardButton> fourNumberOfDecimalPlaces;

            String[] buttonText = new String[]{"2", "3", "4"};
            buttonText[userSettings.getNumberOfDecimalPlaces() - 2] = "✔️" + userSettings.getNumberOfDecimalPlaces();


            twoNumberOfDecimalPlaces = createButton(buttonText[0], "Settings_Number_of_decimal_places_2");

            threeNumberOfDecimalPlaces = createButton(buttonText[1], "Settings_Number_of_decimal_places_3");

            fourNumberOfDecimalPlaces = createButton(buttonText[2], "Settings_Number_of_decimal_places_4");


            ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(twoNumberOfDecimalPlaces);
            buttons.add(threeNumberOfDecimalPlaces);
            buttons.add(fourNumberOfDecimalPlaces);

            InlineKeyboardMarkup keyboard = createKeyboard(buttons);

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).contains("Settings_Number_of_decimal_places_3")) {

            userSettings.setNumberOfDecimalPlaces(3);
            String text = "Оберіть кількість знаків після коми:";

            SendMessage message = createMassage(text, chaId);

            ArrayList<InlineKeyboardButton> twoNumberOfDecimalPlaces;
            ArrayList<InlineKeyboardButton> threeNumberOfDecimalPlaces;
            ArrayList<InlineKeyboardButton> fourNumberOfDecimalPlaces;

            String[] buttonText = new String[]{"2", "3", "4"};

            buttonText[userSettings.getNumberOfDecimalPlaces() - 2] = "✔️" + userSettings.getNumberOfDecimalPlaces();


            twoNumberOfDecimalPlaces = createButton(buttonText[0], "Settings_Number_of_decimal_places_2");

            threeNumberOfDecimalPlaces = createButton(buttonText[1], "Settings_Number_of_decimal_places_3");

            fourNumberOfDecimalPlaces = createButton(buttonText[2], "Settings_Number_of_decimal_places_4");


            ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(twoNumberOfDecimalPlaces);
            buttons.add(threeNumberOfDecimalPlaces);
            buttons.add(fourNumberOfDecimalPlaces);

            InlineKeyboardMarkup keyboard = createKeyboard(buttons);

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Number_of_decimal_places_4")) {

            userSettings.setNumberOfDecimalPlaces(4);
            String text = "Оберіть кількість знаків після коми:";

            SendMessage message = createMassage(text, chaId);

            ArrayList<InlineKeyboardButton> twoNumberOfDecimalPlaces;
            ArrayList<InlineKeyboardButton> threeNumberOfDecimalPlaces;
            ArrayList<InlineKeyboardButton> fourNumberOfDecimalPlaces;

            String[] buttonText = new String[]{"2", "3", "4"};

            buttonText[userSettings.getNumberOfDecimalPlaces() - 2] = "✔️" + userSettings.getNumberOfDecimalPlaces();

            twoNumberOfDecimalPlaces = createButton(buttonText[0], "Settings_Number_of_decimal_places_2");

            threeNumberOfDecimalPlaces = createButton(buttonText[1], "Settings_Number_of_decimal_places_3");

            fourNumberOfDecimalPlaces = createButton(buttonText[2], "Settings_Number_of_decimal_places_4");


            ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(twoNumberOfDecimalPlaces);
            buttons.add(threeNumberOfDecimalPlaces);
            buttons.add(fourNumberOfDecimalPlaces);

            InlineKeyboardMarkup keyboard = createKeyboard(buttons);

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }






    }


    public String getCallbackQueryData(Update update) {
        return update.getCallbackQuery().getData();
    }

    public Long getChatId(Update update) {

        if (update.hasMessage()){
            return update.getMessage().getFrom().getId();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }

        return null;
    }
    public static ArrayList<InlineKeyboardButton> createButton(String text, String callBackData) {
        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text(new String(text.getBytes(), StandardCharsets.UTF_8))
                .callbackData(callBackData)
                .build();
        ArrayList<InlineKeyboardButton> result = new ArrayList<>();
        result.add(button);

        return result;

    }

    public static InlineKeyboardMarkup createKeyboard(ArrayList<ArrayList<InlineKeyboardButton>> buttons) {
        return InlineKeyboardMarkup
                .builder()
                .keyboard(buttons)
                .build();
    }

    public static SendMessage createMassage(String text, Long chaId) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setChatId(chaId);
        return message;
    }



}
