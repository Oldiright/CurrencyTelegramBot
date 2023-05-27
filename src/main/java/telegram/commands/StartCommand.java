package telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;

import java.util.Collections;



public class StartCommand extends BotCommand {

    public StartCommand() {
        super("start", "Start command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {


        String text = "Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют.";

        SendMessage message = new SendMessage();
        message.setText(text);

        message.setChatId(Long.toString(chat.getId()));

        InlineKeyboardButton updateInfo = createButton("Отримати інфо", "Get info");

        InlineKeyboardButton settings = createButton("Налаштування", "Settings");

        ArrayList<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(updateInfo);
        buttons.add(settings);

        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singletonList(buttons))
                .build();

        message.setReplyMarkup(keyboard);

        try {
            absSender.execute(message);

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public static InlineKeyboardButton createButton(String text, String callBackData) {
        return InlineKeyboardButton
                .builder()
                .text(new String(text))
                .callbackData(callBackData)
                .build();

    }


}
