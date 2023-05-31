package telegram.scheduler;

import lombok.Data;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import telegram.CurrencyTelegramBot;
import telegram.GetInfo;
import telegram.UserSettings;
import telegram.settings.utils.Utils;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

@Data

public class AlertScheduler {

    ConcurrentHashMap<String, ConcurrentHashMap<Long, UserSettings>>  scheduler;



    public AlertScheduler() {
        scheduler = new ConcurrentHashMap<>();
    }


     public static void startAlertScheduler(AlertScheduler alertScheduler, CurrencyTelegramBot currencyTelegramBot) {
         ScheduledExecutorService scheduledSendMessage = Executors.newScheduledThreadPool(1);

         scheduledSendMessage.scheduleAtFixedRate(() -> {
             String hourRightNow = String.valueOf(LocalDateTime.now().getHour());

             //в цикле проверяем целые часы и запускаем тот же процесс расписания
             for (int i = 9; i < 19; i++) {

                 if (LocalDateTime.now().getHour() == i
                         && LocalDateTime.now().getMinute() == 0
                         && LocalDateTime.now().getSecond() == 0)

                     //этот код не менял (рассылка) (переменную hourRightNow можно убрать, она равна - "" + i)
                     if (alertScheduler.getScheduler().containsKey(hourRightNow)) {
                         Set<Long> ourChats = alertScheduler.getScheduler().get(hourRightNow).keySet();
                         System.out.println(ourChats);

                         for (Long chatId : ourChats) {
                             try {
                                 currencyTelegramBot.executeAsync(GetInfo.infoMessage(Utils.createUtilUpdate("Get Info"), chatId, alertScheduler.getScheduler().get(hourRightNow).get(chatId)));
                             } catch (TelegramApiException e) {
                                 throw new RuntimeException(e);
                             }
                         }
                     }
             }
         }, 1, 1, SECONDS);

     }
}
