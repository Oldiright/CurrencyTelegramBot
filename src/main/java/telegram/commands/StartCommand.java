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
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class StartCommand extends BotCommand {

    public StartCommand() {
        super("start", "Start command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String text = new String("Ласкаво просимо. Цей бот допоможе відслідковувати актуальні курси валют"
                .getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(chat.getId());

        String getInfo = new String("Отримати інфо".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        String settings = new String("Налаштування".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);

        List<InlineKeyboardButton> buttons = Stream.of(getInfo, settings)
                .map(s -> InlineKeyboardButton
                        .builder()
                        .text(s)
                        .callbackData(s)
                        .build())
                .toList();

        InlineKeyboardMarkup keyboard = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singleton(buttons))
                .build();

        message.setReplyMarkup(keyboard);

        try {
            absSender.execute(message);
        }catch (TelegramApiException ex){
            throw new RuntimeException();
        }
    }
}
