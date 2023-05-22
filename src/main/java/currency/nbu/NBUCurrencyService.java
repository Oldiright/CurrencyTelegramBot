package currency.nbu;

import currency.nbu.dto.NBUCurrencyItem;
import currency.Currency;
import currency.CurrencyService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class NBUCurrencyService implements CurrencyService {
    private final static String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    List<NBUCurrencyItem> list;

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

        Type type = TypeToken.getParameterized(List.class, NBUCurrencyItem.class).getType();
        list = gson.fromJson(rates, type);
    }

    public double getRate(Currency currency) {
        getExchangeRates();
        return list.stream()
                .filter(it -> it.getCc().equalsIgnoreCase(currency.name()))
                .map(NBUCurrencyItem::getRate)
                .findFirst().orElse(-1d);
    }

    @Override
    public double getRateBuy(Currency currency) {
        return getRate(currency);
    }

    // НБУ не має Офіційний курс іноземних валют до гривні - потріьно знайти стороннє API або залишити тільки купівлю
    @Override
    public double getRateSell(Currency currency) {
        return getRate(currency);
    }
}