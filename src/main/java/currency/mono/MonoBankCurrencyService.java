package currency.mono;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import currency.BankAPI;
import currency.Currency;
import currency.CurrencyService;
import currency.mono.dto.MonoBankCurrencyItem;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class MonoBankCurrencyService implements CurrencyService {

    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static LocalDateTime localDateTimePrevRequest;
    private static List<MonoBankCurrencyItem> monoBankCurrencyList;


    private void getExchangeRates(){
        localDateTimePrevRequest = LocalDateTime.now();

        String rates;
        try {
            rates = Jsoup.connect(BankAPI.MONO)
                    .ignoreContentType(true)
                    .get()
                    .text();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Type type = TypeToken.getParameterized(List.class, MonoBankCurrencyItem.class).getType();
        monoBankCurrencyList = gson.fromJson(rates, type);
    }


    @Override
    public double getRateBuy(Currency currency) {

        checkTimeInterval();

        return monoBankCurrencyList.stream()
                .filter(it -> it.getCurrency() == currency)
                .filter(it -> it.getBaseCurrency() == Currency.UAH)
                .map(MonoBankCurrencyItem::getRateBuy)
                .findFirst().orElse(-1d);

    }

    @Override
    public double getRateSell(Currency currency) {

        checkTimeInterval();

        return monoBankCurrencyList.stream()
                .filter(it -> it.getCurrency() == currency)
                .filter(it -> it.getBaseCurrency() == Currency.UAH)
                .map(MonoBankCurrencyItem::getRateSell)
                .findFirst().orElse(-1d);
    }

    //Выполняет проверку времени прошедшего с момента первого запроса к MonoBank API, если запроса не было,
    //вызывает метод getExchangeRates(), который заплняет monoBankCurrencyList и инициализирует переменную
    //localDateTimePrevRequest в которой храниться время первого запроса. Если лист уже создан, проверяет
    //временной интервал прошедший с момента первого запроса к MonoBank API, если разница во времени больше
    //300 секунд, то снова вызывается метод getExchangeRates(), соответствено поле localDateTimePrevRequest
    //инициализируется новым значением
    private void checkTimeInterval(){
        if (monoBankCurrencyList == null) {
            getExchangeRates();
        }else {
            LocalDateTime localDateTimeNew = LocalDateTime.now();
            Duration duration = Duration.between(localDateTimePrevRequest, localDateTimeNew);
            if (duration.toSeconds() > 300) {
                getExchangeRates();
            }
        }
    }

}

