package telegram.scheduler;

import lombok.Data;
import telegram.UserSettings;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
 @Data

public class AlertScheduler {

    ConcurrentHashMap<String, HashMap<Long, UserSettings>>  scheduler;



    public AlertScheduler() {
        scheduler = new ConcurrentHashMap<>();
    }




}
