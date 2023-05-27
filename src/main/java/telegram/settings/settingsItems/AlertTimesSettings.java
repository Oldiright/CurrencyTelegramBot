package telegram.settings.settingsItems;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import telegram.UserSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class AlertTimesSettings {

    private static ReplyKeyboardMarkup createKeyboard(){
        List<KeyboardRow> keyboards = new ArrayList<>();
        for (int i = 9; ; ) {
            int end = ((i + 3) > 19) ? (i + (19 - i)) : (i + 3);
            keyboards.add(createKeyboardLine(i, end));
            i = end;
            if(end == 19) {
                break;
            }
        }
        keyboards.get(keyboards.size() - 1).add("Вимкнути сповіщення");
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setOneTimeKeyboard(true);
        keyboard.setKeyboard(keyboards);
        return keyboard;
    }
    public static SendMessage settingsAlertTimeAlertTimesMessage(Update update, Long chatId, UserSettings userSettings){

        SendMessage message = new  SendMessage();
        message.setChatId(chatId);
        message.setText("Оберіть час: ");
        message.setReplyMarkup(createKeyboard());

        return message;
    }

    private static KeyboardRow createKeyboardLine(int startOfRange, int endOfRange){

        KeyboardRow keyboard = new KeyboardRow();
        IntStream.range(startOfRange, endOfRange)
                .mapToObj(String::valueOf)
                .map(s -> KeyboardButton
                        .builder()
                        .text(s)
                        .build())
                .forEach(keyboard::add);
        return keyboard;
    }

}
