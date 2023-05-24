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


        String text = "ÐÐ°ÑÐºÐ°Ð²Ð¾ Ð¿ÑÐ¾ÑÐ¸Ð¼Ð¾. Ð¦ÐµÐ¹ Ð±Ð¾Ñ Ð´Ð¾Ð¿Ð¾Ð¼Ð¾Ð¶Ðµ Ð²ÑÐ´ÑÐ»ÑÐ´ÐºÐ¾Ð²ÑÐ²Ð°ÑÐ¸ Ð°ÐºÑÑÐ°Ð»ÑÐ½Ñ ÐºÑÑÑÐ¸ Ð²Ð°Ð»ÑÑ";

        SendMessage message = new SendMessage();
        message.setText(new String(text.getBytes(), StandardCharsets.UTF_8));

        message.setChatId(Long.toString(chat.getId()));

        InlineKeyboardButton updateInfo = createButton("ÐÑÑÐ¸Ð¼Ð°ÑÐ¸ ÑÐ½ÑÐ¾", "Get info");

        InlineKeyboardButton settings = createButton("ÐÐ°Ð»Ð°ÑÑÑÐ²Ð°Ð½Ð½Ñ", "Settings");

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
                .text(new String(text.getBytes(), StandardCharsets.UTF_8))
                .callbackData(callBackData)
                .build();

    }


}
