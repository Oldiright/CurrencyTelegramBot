package telegram;

import lombok.Data;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.scheduler.AlertScheduler;
import telegram.settings.utils.Utils;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;


@Data
public class TelegramBotService {
    private final CurrencyTelegramBot currencyTelegramBot;
    private AlertScheduler alertScheduler;

    {
        try {
            alertScheduler = Utils.getAlertScheduler();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public TelegramBotService(){

        currencyTelegramBot = new CurrencyTelegramBot(alertScheduler);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(currencyTelegramBot);
        }catch (TelegramApiException ex){
            ex.printStackTrace();
        }

        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(currencyTelegramBot::saveData, 9, 21, SECONDS);

        ScheduledExecutorService schedulerForScanner = Executors.newScheduledThreadPool(1);

        schedulerForScanner.scheduleAtFixedRate(() -> {

            Scanner scanner = new Scanner(System.in);
            if (scanner.next().equalsIgnoreCase("exit")) {
                currencyTelegramBot.saveData();
                System.exit(0);
            } else {
                scanner.close();
            }
        }, 2, 2, SECONDS);


        AlertScheduler.startAlertScheduler(alertScheduler, currencyTelegramBot);
    }
}
