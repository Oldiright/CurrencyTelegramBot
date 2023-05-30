package telegram.commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.CurrencyTelegramBot;
import telegram.GetInfo;

public class GetCommand extends BotCommand {
    public GetCommand(){
        super("get", "Get command");

    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        Update update = new Update();
        CallbackQuery query = new CallbackQuery();
        query.setData("Get Info");
        update.setCallbackQuery(query);
        SendMessage message = GetInfo.infoMessage(update, user.getId(), CurrencyTelegramBot.getUserSettings().get(user.getId()));
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
