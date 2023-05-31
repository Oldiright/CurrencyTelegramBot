package telegram;

import com.google.gson.Gson;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.FileReader;

@Data
class BotConstants {

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


