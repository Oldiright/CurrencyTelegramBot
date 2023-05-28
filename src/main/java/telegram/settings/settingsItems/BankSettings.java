package telegram.settings.settingsItems;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.UserSettings;
import telegram.settings.utils.Utils;

import java.util.ArrayList;
import java.util.stream.Stream;

public class BankSettings {

    public static SendMessage settingsBankMessage(Update update, Long chatId, UserSettings userSettings){
        String text = "Оберіть банк:";
        ArrayList<ArrayList<InlineKeyboardButton>> buttons =  new ArrayList<>();
                Stream.of("NBU", "Privat", "Mono")
                .map(s -> userSettings.getBankName()
                        .equals(s) ? "✔️" + s : s)
                .map(s -> Utils.createButtonForColumnsKeyboard(s, "Settings_Bank_" + s.replaceAll("✔️", "")))
                .forEach(buttons::add);

                buttons.add(Utils.createButtonForColumnsKeyboard("До налаштувань", "Settings"));

        InlineKeyboardMarkup keyboard = Utils.createColumnsKeyboard(buttons);
        SendMessage message = Utils.createMessage(text, chatId);
        message.setReplyMarkup(keyboard);
        return message;
    }
}
