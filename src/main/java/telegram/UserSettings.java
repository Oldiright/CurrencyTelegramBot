package telegram;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserSettings {

    private int NumberOfDecimalPlaces;
    private String bankName;
    private boolean euro;
    private boolean usd;
    private String alertTimes;
    private boolean needAlertTimes;
    private int lastAlert;


    public UserSettings() {
        NumberOfDecimalPlaces = 2;
        bankName = "NBU";
        euro = true;
        usd = true;
        alertTimes = null;
        needAlertTimes = false;



    }

}
