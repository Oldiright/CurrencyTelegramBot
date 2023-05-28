package telegram;

import lombok.Data;

@Data
public class UserSettings {
    private long chatId;
    private int NumberOfDecimalPlaces;
    private String bankName;
    private boolean euro;
    private boolean usd;
    private String alertTimes;
    private boolean needAlertTimes;


    public UserSettings() {
        NumberOfDecimalPlaces = 2;
        bankName = "NBU";
        euro = true;
        usd = true;
        alertTimes = null;
        needAlertTimes = true;



    }

}
