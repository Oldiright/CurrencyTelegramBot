package telegram.settings.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Utils {

    public static SendMessage createMessage(String text, Long chatId) {
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chatId);
        return message;
    }

    public static ArrayList<InlineKeyboardButton> createButtonForColumnsKeyboard(String text, String callBackData) {
        InlineKeyboardButton button = InlineKeyboardButton
                .builder()
                .text(text)
                .callbackData(callBackData)
                .build();
        ArrayList<InlineKeyboardButton> result = new ArrayList<>();
        result.add(button);

        return result;

    }

    public static InlineKeyboardButton createButtonForLinerKeyboard(String text, String callBackData) {

        return InlineKeyboardButton
                .builder()
                .text(text)
                .callbackData(callBackData)
                .build();

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

    public static InlineKeyboardMarkup getGeneralMenuKeyboard() {
        InlineKeyboardButton updateInfo = createButtonForLinerKeyboard("Отримати інфо", "Get Info");

        InlineKeyboardButton settings = createButtonForLinerKeyboard("Налаштування", "Settings");

        ArrayList<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(updateInfo);
        buttons.add(settings);

    return InlineKeyboardMarkup
            .builder()
            .keyboard(Collections.singletonList(buttons))
            .build();
    }

    //визначення кількості часу до наступної години;
    public static int getMillisBeforeNextHour() {
        LocalDateTime localDateTime = LocalDateTime.now();

        int[] timeBeforeNextHour = new int[]{60 - localDateTime.getMinute(), 60 - localDateTime.getSecond()};

        return ((timeBeforeNextHour[0] * 60 + timeBeforeNextHour[1])) * 1000;
    }

    public static Update createUtilUpdate(String callbackQueryData) {
        Update update = new Update();
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData(callbackQueryData);
        update.setCallbackQuery(callbackQuery);

        return update;
    }
}
