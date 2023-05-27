package telegram;

import lombok.Data;

@Data
public class UserSettings {
    private int NumberOfDecimalPlaces;
    private String bankName;
    private boolean euro;
    private boolean usd;
    private String alertTimes;
    boolean needAlertTimes;


    public UserSettings() {
        NumberOfDecimalPlaces = 2;
        bankName = "NBU";
        euro = true;
        usd = true;
        alertTimes = null;
        needAlertTimes = true;



    }

}
