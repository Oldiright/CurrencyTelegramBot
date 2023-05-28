package telegram;

import lombok.Data;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.scheduler.AlertScheduler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

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


        //изначення кількості часу до наступної години;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String serverStartTime = dtf.format(now);
        System.out.println(serverStartTime);
        String[] dataAndTimeArray = serverStartTime.split( " ");

        String[] time = dataAndTimeArray[1].split(":");

        String[] timeBeforeNextHour = new String[]{
                String.valueOf(60 - Integer.parseInt(time[1])),
                String.valueOf(60 - Integer.parseInt(time[2]))};

        long millisBeforeNextHour = (Long.parseLong(timeBeforeNextHour[0]) * 60 + Long.parseLong(timeBeforeNextHour[1])) * 1000;


        //очікування до наступної години;

        try {
            Thread.sleep(millisBeforeNextHour);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        while (true) {
            LocalDateTime timeNow = LocalDateTime.now();
            System.out.println(dtf.format(timeNow));
            String hourRightNow = dtf.format(timeNow).split(" ")[1].split(":")[0];
            System.out.println(hourRightNow);



            if(alertScheduler.getScheduler().containsKey(hourRightNow)) {
                Set<Long> ourChats = alertScheduler.getScheduler().get(hourRightNow).keySet();
                System.out.println(ourChats);

                for (Long id: ourChats) {
                    try {
                        Update updateForGetIfo = new Update();
                        CallbackQuery callbackQuery = new CallbackQuery();
                        callbackQuery.setData("Get Info");
                        updateForGetIfo.setCallbackQuery(callbackQuery);
                        currencyTelegramBot.executeAsync(GetInfo.infoMessage(updateForGetIfo, id, alertScheduler.getScheduler().get(hourRightNow).get(id)));
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                }


            }
            try {
                Thread.sleep(3600000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }


    }
}
