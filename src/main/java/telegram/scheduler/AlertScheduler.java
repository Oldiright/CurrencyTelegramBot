package telegram.scheduler;

import lombok.Data;
import telegram.UserSettings;
import java.util.concurrent.ConcurrentHashMap;
 @Data

public class AlertScheduler {

    ConcurrentHashMap<String, ConcurrentHashMap<Long, UserSettings>>  scheduler;



    public AlertScheduler() {
        scheduler = new ConcurrentHashMap<>();
    }




}
