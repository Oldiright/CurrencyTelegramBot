package telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.commands.StartCommand;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


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

        Long chatId = getChatId(update);
        System.out.println(chatId);


        if(update.hasCallbackQuery()) {
            System.out.println(getCallbackQueryData(update));
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings")) {

            InlineKeyboardMarkup keyboard;
            String text = "Налаштування:";

            SendMessage message = createMassage(text, chatId);

            ArrayList<InlineKeyboardButton> numberOfDecimalPlaces = createButton("Кількість знаків після коми", "Settings_Number_of_decimal_places");

            ArrayList<InlineKeyboardButton> bank = createButton("Банк", "Settings_Bank");

            ArrayList<InlineKeyboardButton>  currency = createButton("Валюти", "Settings_Currency");

            ArrayList<InlineKeyboardButton>  alertTimes = createButton("Час оповіщень", "Settings_Alert_times");





            ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(numberOfDecimalPlaces);
            buttons.add(bank);
            buttons.add(currency);
            buttons.add(alertTimes);

            keyboard = createKeyboard(buttons);

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);

        }
        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Number_of_decimal_places")) {

            String text = "Оберіть кількість знаків після коми:";

            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsNumberOfDecimalPlaces(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Number_of_decimal_places_2")) {

            userSettings.setNumberOfDecimalPlaces(2);
            String text = "Оберіть кількість знаків після коми:";

            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsNumberOfDecimalPlaces(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).contains("Settings_Number_of_decimal_places_3")) {

            userSettings.setNumberOfDecimalPlaces(3);
            String text = "Оберіть кількість знаків після коми:";

            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsNumberOfDecimalPlaces(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Number_of_decimal_places_4")) {

            userSettings.setNumberOfDecimalPlaces(4);
            String text = "Оберіть кількість знаків після коми:";

            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsNumberOfDecimalPlaces(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Bank")) {
            String text = "Оберіть банк:";
            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsBanks(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Bank_NBU")) {
            userSettings.setBankName("NBU");

            String text = "Оберіть банк:";
            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsBanks(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Bank_Privat")) {
            userSettings.setBankName("Privat");

            String text = "Оберіть банк:";
            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsBanks(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Bank_Mono")) {
            userSettings.setBankName("Mono");

            String text = "Оберіть банк:";
            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsBanks(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Currency")) {

            String text = "Оберіть валюти:";
            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsCurrency(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Currency_USD")) {
            userSettings.setUsd(!userSettings.isUsd());

            String text = "Оберіть валюти:";
            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsCurrency(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Currency_EUR")) {
            userSettings.setEuro(!userSettings.isEuro());

            String text = "Оберіть валюти:";
            SendMessage message = createMassage(text, chatId);

            InlineKeyboardMarkup keyboard = createKeyboard(getButtonsForSettingsCurrency(userSettings));

            message.setReplyMarkup(keyboard);

            sendApiMethodAsync(message);
        }

        if(update.hasCallbackQuery() & getCallbackQueryData(update).equals("Settings_Alert_times")) {

            String text = "Оберіть час сповіщення:";
            SendMessage message = createMassage(text, chatId);

            KeyboardRow firstLine = new KeyboardRow();
            addButtonsForKeyBordRow(9,11, firstLine);

            KeyboardRow secondLine = new KeyboardRow();

            addButtonsForKeyBordRow(12, 14, secondLine);

            KeyboardRow thirdLine = new KeyboardRow();
            addButtonsForKeyBordRow(15, 17, thirdLine);

            KeyboardRow lastLine = new KeyboardRow();

            lastLine.add(KeyboardButton.builder().text(Integer.toString(18)).build());
            lastLine.add(KeyboardButton.builder().text(new String("Вимкнути сповіщення".getBytes(), StandardCharsets.UTF_8))
                    .build());
            List<KeyboardRow> keyboard = new ArrayList<>();
            keyboard.add(firstLine);
            keyboard.add(secondLine);
            keyboard.add(thirdLine);
            keyboard.add(lastLine);

            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            keyboardMarkup.setOneTimeKeyboard(true);
            
            keyboardMarkup.setKeyboard(keyboard);

            message.setReplyMarkup(keyboardMarkup);

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

    public ArrayList<ArrayList<InlineKeyboardButton>> getButtonsForSettingsNumberOfDecimalPlaces(UserSettings userSettings) {

        ArrayList<InlineKeyboardButton> twoNumberOfDecimalPlaces;
        ArrayList<InlineKeyboardButton> threeNumberOfDecimalPlaces;
        ArrayList<InlineKeyboardButton> fourNumberOfDecimalPlaces;
        ArrayList<InlineKeyboardButton> settings;

        String[] buttonText = new String[]{"2", "3", "4"};

        buttonText[userSettings.getNumberOfDecimalPlaces() - 2] = "✔️" + userSettings.getNumberOfDecimalPlaces();

        twoNumberOfDecimalPlaces = createButton(buttonText[0], "Settings_Number_of_decimal_places_2");

        threeNumberOfDecimalPlaces = createButton(buttonText[1], "Settings_Number_of_decimal_places_3");

        fourNumberOfDecimalPlaces = createButton(buttonText[2], "Settings_Number_of_decimal_places_4");

        settings = createButton("До меню налаштувань", "Settings");

        ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(twoNumberOfDecimalPlaces);
        buttons.add(threeNumberOfDecimalPlaces);
        buttons.add(fourNumberOfDecimalPlaces);
        buttons.add(settings);

        return buttons;
    }

    public ArrayList<ArrayList<InlineKeyboardButton>> getButtonsForSettingsBanks(UserSettings userSettings) {

        String[] buttonText = new String[]{"НБУ", "ПриватБанк", "МоноБанк"};

        if(userSettings.getBankName().equals("NBU")) {
            buttonText[0] = "✔️" + buttonText[0];
        }
        if(userSettings.getBankName().equals("Privat")) {
            buttonText[1] = "✔️" + buttonText[1];
        }
        if (userSettings.getBankName().equals("Mono")) {
            buttonText[2] = "✔️" + buttonText[2];
        }

        ArrayList<InlineKeyboardButton> bankNbu = createButton(buttonText[0], "Settings_Bank_NBU");

        ArrayList<InlineKeyboardButton> bankPrivat = createButton(buttonText[1], "Settings_Bank_Privat");

        ArrayList<InlineKeyboardButton>  bankMono = createButton(buttonText[2], "Settings_Bank_Mono");

        ArrayList<InlineKeyboardButton> settings = createButton("До меню налаштувань", "Settings");

        ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(bankNbu);
        buttons.add(bankPrivat);
        buttons.add(bankMono);
        buttons.add(settings);

        return buttons;
    }

    public ArrayList<ArrayList<InlineKeyboardButton>> getButtonsForSettingsCurrency(UserSettings userSettings) {

        String[] buttonText = new String[]{"USD", "EUR"};

        if(userSettings.isUsd()) {
            buttonText[0] = "✔️" + buttonText[0];
        }
        if(userSettings.isEuro()) {
            buttonText[1] = "✔️" + buttonText[1];
        }

        ArrayList<InlineKeyboardButton> currencyUsd = createButton(buttonText[0], "Settings_Currency_USD");

        ArrayList<InlineKeyboardButton> currencyEur = createButton(buttonText[1], "Settings_Currency_EUR");

        ArrayList<InlineKeyboardButton> settings = createButton("До меню налаштувань", "Settings");

        ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
        buttons.add(currencyUsd);
        buttons.add(currencyEur);
        buttons.add(settings);

        return buttons;
    }

    public static void addButtonsForKeyBordRow(int startValue, int lastValue, KeyboardRow keyboardRow) {
        for (int i = startValue; i <= lastValue; i++) {
            KeyboardButton button = KeyboardButton
                    .builder()
                    .text(Integer.toString(i))
                    .build();
            keyboardRow.add(button);
        }
    }





}
