package currency.mono;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import currency.Currency;
import currency.CurrencyService;
import currency.mono.dto.MonoBankCurrencyItem;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MonoBankCurrencyService implements CurrencyService {
    private final static String url = "https://api.monobank.ua/bank/currency";
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    List<MonoBankCurrencyItem> list;

    private void getExchangeRates(){
        String rates;
        try {
            rates = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .get()
                    .text();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Type type = TypeToken.getParameterized(List.class, MonoBankCurrencyItem.class).getType();
        list = gson.fromJson(rates, type);
    }


    @Override
    public double getRateBuy(Currency currency) {
        getExchangeRates();
        return list.stream()
                .filter(it -> it.getCurrency() == currency)
                .filter(it -> it.getBaseCurrency() == Currency.UAH)
                .map(MonoBankCurrencyItem::getRateBuy)
                .findFirst().orElse(-1d);

    }

    @Override
    public double getRateSell(Currency currency) {
        getExchangeRates();
        return list.stream()
                .filter(it -> it.getCurrency() == currency)
                .filter(it -> it.getBaseCurrency() == Currency.UAH)
                .map(MonoBankCurrencyItem::getRateSell)
                .findFirst().orElse(-1d);

    }
    
}

