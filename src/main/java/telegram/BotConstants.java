package telegram;

import com.google.gson.Gson;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Data
class BotConstants {

//    public static final String BOT_NAME = "t.me/My_branch_test_new_currency_bot";
//    public static final String BOT_TOKEN = "6159683556:AAFs4iCHz6jNO3b60uu4TT9oBjW9gRDJKJA";

    public static final String CONFIG_PATH = "config.json";

    public BotConfig botInit() {
        try {
            return new Gson().fromJson(new FileReader(CONFIG_PATH), BotConfig.class);
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}

@Data
    class BotConfig {
        private String botName;
        private String botToken;
    }


