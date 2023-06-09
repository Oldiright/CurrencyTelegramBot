package telegram.settings.settingsItems;


import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.UserSettings;
import telegram.settings.utils.Utils;

import java.util.ArrayList;
import java.util.stream.Stream;

public class BankSettings {

    public static BotApiMethod settingsBankMessage(Update update, Long chatId, UserSettings userSettings){
        String text = "Оберіть банк:";

        if(update.getCallbackQuery().getData().contains("NBU")
                || update.getCallbackQuery().getData().contains("Mono")
                || update.getCallbackQuery().getData().contains("Privat")){
            userSettings.setBankName(update.getCallbackQuery().getData().replaceAll("Settings_Bank_", ""));
        }

        ArrayList<ArrayList<InlineKeyboardButton>> buttons =  new ArrayList<>();
                Stream.of(translatorToEng( "НБУ"),translatorToEng( "ПриватБанк"), translatorToEng( "МоноБанк"))
                .map(s -> userSettings.getBankName()
                        .equals(s) ? "✔️" + translatorToUkr(s) : translatorToUkr(s))
                .map(s -> Utils.createButtonForColumnsKeyboard(s, "Settings_Bank_" + translatorToEng(s.replaceAll("✔️", ""))))
                .forEach(buttons::add);

                buttons.add(Utils.createButtonForColumnsKeyboard("До меню налаштувань", "Settings"));
        buttons.add(Utils.createButtonForColumnsKeyboard("До головного меню", "To Start"));

        InlineKeyboardMarkup keyboard = Utils.createColumnsKeyboard(buttons);

        if(update.getCallbackQuery().getData().equals("Settings_Bank")) {
            return SendMessage.builder()
                    .text(text)
                    .chatId(chatId)
                    .replyMarkup(keyboard)
                    .build();

        } else {

            return EditMessageReplyMarkup.builder()
                    .replyMarkup(keyboard)
                    .chatId(chatId)
                    .messageId(update.getCallbackQuery().getMessage().getMessageId())
                    .build();
        }


    }

    private static String translatorToUkr(String input){
        if(input.equals("NBU"))
            return "НБУ";
        if (input.equals("Mono"))
            return "МоноБанк";
        else return "ПриватБанк";
    }

    private static String translatorToEng(String input){
        if(input.equals("НБУ"))
            return "NBU";
        if (input.equals("МоноБанк"))
            return "Mono";
        else return "Privat";
    }
}
