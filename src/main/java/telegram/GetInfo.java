package telegram;

import currency.Currency;
import currency.CurrencyService;
import currency.nbu.NBUCurrencyService;
import currency.mono.MonoBankCurrencyService;
import currency.privat.PrivatBankCurrencyService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import telegram.settings.utils.Utils;

import java.util.*;


public class GetInfo {
    public static final String TITLE = "Get Info";
    private static final String TO_START = "To Start";
    private static final String MESSAGE_TO_START = "До головного меню";

//    private static final String NBU = "НБУ";
//    private static final String MONO = "МоноБанк";
//    private static final String PRIVAT = "ПриватБанк";

    private static final String[] NBU = {"NBU", "НБУ"};
    private static final String[] MONO = {"Mono", "МоноБанк"};
    private static final String[] PRIVAT = {"Privat", "ПриватБанк"};


    public static SendMessage infoMessage(Update update, Long chatId, UserSettings userSettings) {

        SendMessage message = null;
        String bankName = userSettings.getBankName();
        String[][] banks = new String[][]{NBU, MONO, PRIVAT};

        if (update.hasCallbackQuery() & getCallbackQueryData(update).equals(TITLE)) {
            CustomHashMap<String, String> getInfoMap = new CustomHashMap<>();

            CurrencyService service = userSettings.getBankName().equals("NBU")
                    ? new NBUCurrencyService()
                    : userSettings.getBankName().equals("Privat")
                    ? new PrivatBankCurrencyService()
                    : new MonoBankCurrencyService();

            for (String[] bank : banks) {
                if (bank[0].equals(bankName))
                    getInfoMap.put("bankName", bank[1]);
            }

            if (userSettings.isUsd()) {
                addRateToMap(Currency.USD, service, getInfoMap, userSettings);
            }

            if (userSettings.isEuro()) {
                addRateToMap(Currency.EUR, service, getInfoMap, userSettings);
            }


            String text = getInfoMap.printInfo();
            message = Utils.createMessage(text, chatId);
            ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Utils.createButtonForColumnsKeyboard(MESSAGE_TO_START, TO_START));
            InlineKeyboardMarkup keyboard = Utils.createColumnsKeyboard(buttons);
            message.setReplyMarkup(keyboard);
        }
        return message;
    }

    public static void addRateToMap(Currency currencyEnum, CurrencyService bankAPIService, CustomHashMap<String, String> getInfoMap, UserSettings userSettings) {
        String currency = currencyEnum.toString();
        int decimal = userSettings.getNumberOfDecimalPlaces();

        if (bankAPIService.getClass().equals(NBUCurrencyService.class)) {
            getInfoMap.put(currency, currency);
            String rate = roundedRate(new NBUCurrencyService().getRate(currencyEnum), decimal);
            getInfoMap.put("rate" + currency, rate);
        } else {
            getInfoMap.put(currency, currency);
            String rateBuy = roundedRate(bankAPIService.getRateBuy(currencyEnum), decimal);
            getInfoMap.put("rateBuy" + currency, rateBuy);
            String rateSell = roundedRate(bankAPIService.getRateSell(currencyEnum), decimal);
            getInfoMap.put("rateSell" + currency, rateSell);
        }
    }

    public static String getCallbackQueryData(Update update) {
        return update.getCallbackQuery().getData();
    }

    public static String roundedRate(double rate, int numberOfDecimals) {

        double decimal = Math.pow(10d, numberOfDecimals);
        StringBuilder result = new StringBuilder(Double.toString(Math.round(rate * decimal) / decimal));

        if (result.length() < result.toString().split("\\.")[0].length() + 1 + numberOfDecimals) {
            while (result.length() != 3 + numberOfDecimals)
                result.append("0");
        }
        return result.toString();
    }
}


class CustomHashMap<K, V> {
    private Map<K, V> map;

    public CustomHashMap() {
        map = new LinkedHashMap<>();
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public String printInfo() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();

            if (key.equals("bankName")) {
                sb.append("Курс від ").append(value).append(":\n");
            } else if (key.equals("USD") || key.equals("EUR")) {
                sb.append("\n").append(value).append("/UAH\n");
            } else if (key.equals("rateUSD") || key.equals("rateEUR")) {
                sb.append(value).append("\n");
            } else if (key.equals("rateBuyUSD") || key.equals("rateBuyEUR")) {
                sb.append("Продаж: ").append(value).append("\n");
            } else if (key.equals("rateSellUSD") || key.equals("rateSellEUR")) {
                sb.append("Купівля: ").append(value).append("\n");
            }
        }
        return sb.toString();
    }
}
