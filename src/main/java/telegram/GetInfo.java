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

import java.nio.charset.StandardCharsets;
import java.util.*;


public class GetInfo {
    public static final String TITLE = "Get Info";
    private static final String MESSAGE_TEXT = "Отримати інфо";
    private static final String MESSAGE_TO_START = "До початкового меню";
    private static final String PRIVAT = "ПриватБанк";
    private static final String MONO = "МоноБанк";
    private static final String NBU = "НБУ";

    public static SendMessage infoMessage(Update update, Long chatId, UserSettings userSettings) {

        SendMessage message = Utils.createMessage(MESSAGE_TEXT, chatId);

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

            InlineKeyboardButton toStart = InlineKeyboardButton.builder()
                    .text(new String(MESSAGE_TO_START.getBytes(), StandardCharsets.UTF_8))
                    .callbackData("To Start")
                    .build();

            InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
                    .keyboard(Collections.singleton(Collections.singletonList(toStart)))
                    .build();

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
            } else if (key.equals("USD")) {
                sb.append("\n").append(value).append("/UAH\n");
            } else if (key.equals("EUR")) {
                sb.append("\n").append(value).append("/UAH\n");
            } else if (key.equals("rateUSD")) {
                sb.append(value).append("\n");
            } else if (key.equals("rateEUR")) {
                sb.append(value).append("\n");
            } else if (key.equals("rateBuyUSD")) {
                sb.append("Продаж: ").append(value).append("\n");
            } else if (key.equals("rateSellUSD")) {
                sb.append("Купівля: ").append(value).append("\n");
            } else if (key.equals("rateBuyEUR")) {
                sb.append("Продаж: ").append(value).append("\n");
            } else if (key.equals("rateSellEUR")) {
                sb.append("Купівля: ").append(value).append("\n");
            }
        }
        return sb.toString();
    }
}
