package telegram.settings.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Utils {

    public static SendMessage createMessage(String text, Long chatId) {
        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));
        message.setChatId(chatId);
        return message;
    }

    public static ArrayList<InlineKeyboardButton> createButtonForColumnsKeyboard(String text, String callBackData) {
        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text(new String(text.getBytes(), StandardCharsets.UTF_8))
                .callbackData(callBackData)
                .build();
        ArrayList<InlineKeyboardButton> result = new ArrayList<>();
        result.add(button);

        return result;

    }

    public static InlineKeyboardMarkup createColumnsKeyboard(ArrayList<ArrayList<InlineKeyboardButton>> buttons) {
        return InlineKeyboardMarkup
                .builder()
                .keyboard(buttons)
                .build();
    }

    public static Long getChatId(Update update) {

        if (update.hasMessage()){
            return update.getMessage().getFrom().getId();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }

        return null;
    }
}
