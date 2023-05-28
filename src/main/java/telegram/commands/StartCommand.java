package telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.settings.utils.Utils;

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

        message.setReplyMarkup(Utils.getGeneralMenuKeyboard());

        try {
            absSender.execute(message);

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

}
