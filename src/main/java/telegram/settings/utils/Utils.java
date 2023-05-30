package telegram.settings.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.UserSettings;
import telegram.scheduler.AlertScheduler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

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

        int[] timeBeforeNextHour = new int[]{59 - localDateTime.getMinute(), 60 - localDateTime.getSecond()};

        return ((timeBeforeNextHour[0] * 60 + timeBeforeNextHour[1])) * 1000;
    }

    public static Update createUtilUpdate(String callbackQueryData) {
        Update update = new Update();
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData(callbackQueryData);
        update.setCallbackQuery(callbackQuery);

        return update;
    }


    public static ConcurrentHashMap<Long, UserSettings> getUserSettingsData() throws FileNotFoundException {
        FileReader fr = new FileReader("usersettings.json");
        GsonBuilder gsonBuilder = new GsonBuilder();

// добавить закриття стріму
        return gsonBuilder
                .create()
                .fromJson(fr, new TypeToken<ConcurrentHashMap<Long, UserSettings>>(){}.getType());
    }


    public static void writerInTheBase(ConcurrentHashMap<Long, UserSettings> userSettings) throws IOException {
        FileWriter fr = new FileWriter("usersettings.json");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(userSettings);
        fr.write(result);
        fr.flush();
        fr.close();
    }

    public static AlertScheduler getAlertScheduler() throws FileNotFoundException {
        FileReader fr = new FileReader("usersettings.json");
        GsonBuilder gsonBuilder = new GsonBuilder();
        AlertScheduler alertScheduler = new AlertScheduler();
        alertScheduler.setScheduler(gsonBuilder
                .create()
                .fromJson(fr, new TypeToken<ConcurrentHashMap<String, ConcurrentHashMap<Long, UserSettings>>>(){}.getType()));
// добавить закриття стріму
        return alertScheduler;
    }


    public static void writerInTheBase(AlertScheduler alertScheduler) throws IOException {
        FileWriter fr = new FileWriter("alertscheduler.json");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = gson.toJson(alertScheduler.getScheduler());
        fr.write(result);
        fr.flush();
        fr.close();
    }



}
