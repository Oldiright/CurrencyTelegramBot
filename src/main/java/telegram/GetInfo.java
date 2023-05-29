package telegram;

import currency.Currency;
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
    private static final String MESSAGE_TEXT = "Отримати інфо";
    private static final String TO_START = "To Start";
    private static final String MESSAGE_TO_START = "До головного меню";
    private static final String NBU = "НБУ";
    private static final String MONO = "МоноБанк";
    private static final String PRIVAT = "ПриватБанк";

    public static SendMessage infoMessage(Update update, Long chatId, UserSettings userSettings) {

        SendMessage message = null;

        if (update.hasCallbackQuery() & getCallbackQueryData(update).equals(TITLE)) {

            CustomHashMap<String, String> getInfoMap = new CustomHashMap<>();
            Currency currency;

            if (userSettings.getBankName().equals("NBU")) {
                getInfoMap.put("bankName", NBU);

                if (userSettings.isUsd()) {
                    currency = Currency.USD;
                    getInfoMap.put("USD", "" + currency);
                    double rate = roundedRate(new NBUCurrencyService().getRate(currency), userSettings.getNumberOfDecimalPlaces());
                    getInfoMap.put("rateUSD", "" + rate);
                }

                if (userSettings.isEuro()) {
                    currency = Currency.EUR;
                    getInfoMap.put("EUR", "" + currency);
                    double rate = roundedRate(new NBUCurrencyService().getRate(currency), userSettings.getNumberOfDecimalPlaces());
                    getInfoMap.put("rateEUR", "" + rate);
                }
            }

            if (userSettings.getBankName().equals("Mono")) {
                getInfoMap.put("bankName", MONO);

                if (userSettings.isUsd()) {
                    currency = Currency.USD;
                    getInfoMap.put("USD", "" + currency);
                    double rateBuyUSD = roundedRate(new MonoBankCurrencyService().getRateBuy(currency), userSettings.getNumberOfDecimalPlaces());
                    getInfoMap.put("rateBuyUSD", "" + rateBuyUSD);
                    double rateSellUSD = roundedRate(new MonoBankCurrencyService().getRateSell(currency), userSettings.getNumberOfDecimalPlaces());
                    getInfoMap.put("rateSellUSD", "" + rateSellUSD);
                }

                if (userSettings.isEuro()) {
                    currency = Currency.EUR;
                    getInfoMap.put("EUR", "" + currency);
                    double rateBuyEUR = roundedRate(new MonoBankCurrencyService().getRateBuy(currency), userSettings.getNumberOfDecimalPlaces());
                    getInfoMap.put("rateBuyEUR", "" + rateBuyEUR);
                    double rateSellEUR = roundedRate(new MonoBankCurrencyService().getRateSell(currency), userSettings.getNumberOfDecimalPlaces());
                    getInfoMap.put("rateSellEUR", "" + rateSellEUR);
                }
            }

            if (userSettings.getBankName().equals("Privat")) {
                getInfoMap.put("bankName", PRIVAT);

                if (userSettings.isUsd()) {
                    currency = Currency.USD;
                    getInfoMap.put("USD", "" + currency);
                    double rateBuyUSD = roundedRate(new PrivatBankCurrencyService().getRateBuy(currency), userSettings.getNumberOfDecimalPlaces());
                    getInfoMap.put("rateBuyUSD", "" + rateBuyUSD);
                    double rateSellUSD = roundedRate(new PrivatBankCurrencyService().getRateSell(currency), userSettings.getNumberOfDecimalPlaces());
                    getInfoMap.put("rateSellUSD", "" + rateSellUSD);
                }

                if (userSettings.isEuro()) {
                    currency = Currency.EUR;
                    getInfoMap.put("EUR", "" + currency);
                    double rateBuyEUR = roundedRate(new PrivatBankCurrencyService().getRateBuy(currency), userSettings.getNumberOfDecimalPlaces());
                    getInfoMap.put("rateBuyEUR", "" + rateBuyEUR);
                    double rateSellEUR = roundedRate(new PrivatBankCurrencyService().getRateSell(currency), userSettings.getNumberOfDecimalPlaces());
                    getInfoMap.put("rateSellEUR", "" + rateSellEUR);
                }
            }

            String text = getInfoMap.toString();

            message = Utils.createMessage(text, chatId);

            ArrayList<ArrayList<InlineKeyboardButton>> buttons = new ArrayList<>();
            buttons.add(Utils.createButtonForColumnsKeyboard(MESSAGE_TO_START, TO_START));

            InlineKeyboardMarkup keyboard = Utils.createColumnsKeyboard(buttons);

            message.setReplyMarkup(keyboard);

        }
        return message;
    }

    public static String getCallbackQueryData(Update update) {
        return update.getCallbackQuery().getData();
    }


    public static double roundedRate(double rate, int numberOfDecimals) {

        double decimal = Math.pow(10d, numberOfDecimals);

        return Math.round(rate * decimal) / decimal;
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();

            if (key.equals("bankName")) {
                sb.append("Курс в ").append(value).append(":\n");
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
