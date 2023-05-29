package telegram;

import lombok.Data;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.scheduler.AlertScheduler;
import telegram.settings.utils.Utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import static telegram.settings.utils.Utils.getMillisBeforeNextHour;


@Data
public class TelegramBotService {
    private final CurrencyTelegramBot currencyTelegramBot;
    private AlertScheduler alertScheduler = new AlertScheduler();

    public TelegramBotService(){

        currencyTelegramBot = new CurrencyTelegramBot(alertScheduler);


        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(currencyTelegramBot);
        }catch (TelegramApiException ex){
            ex.printStackTrace();
        }

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        //очікування до наступної години;

        try {
            Thread.sleep(getMillisBeforeNextHour());
//            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        while (true) {
           String hourRightNow = String.valueOf(LocalDateTime.now().getHour());

            if(alertScheduler.getScheduler().containsKey(hourRightNow)) {
                Set<Long> ourChats = alertScheduler.getScheduler().get(hourRightNow).keySet();
                System.out.println(ourChats);

                for (Long chatId: ourChats) {
                    try {
                        currencyTelegramBot.executeAsync(GetInfo.infoMessage(Utils.createUtilUpdate("Get Info"), chatId, alertScheduler.getScheduler().get(hourRightNow).get(chatId)));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            //очікування до наступної години;

            try {
                Thread.sleep(getMillisBeforeNextHour());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

}
