package currency.privat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import currency.BankAPI;
import currency.Currency;
import currency.CurrencyService;
import currency.privat.dto.PrivatBankCurrencyItem;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
public class PrivatBankCurrencyService implements CurrencyService{
        private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
        private List<PrivatBankCurrencyItem> list;

        private void getExchangeRates() {
            String rates;
            try {
                rates = Jsoup.connect(BankAPI.PRIVAT)
                        .ignoreContentType(true)
                        .get()
                        .text();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Type type = TypeToken.getParameterized(List.class, PrivatBankCurrencyItem.class).getType();
            list = gson.fromJson(rates, type);
        }

        @Override
        public double getRateBuy(Currency currency) {
            getExchangeRates();
            return list.stream()
                    .filter(it -> it.getCcy() == currency)
                    .filter(it -> it.getBase_ccy() == Currency.UAH)
                    .mapToDouble(PrivatBankCurrencyItem::getBuy)
                    .findFirst()
                    .orElse(-1d);
        }

        @Override
        public double getRateSell(Currency currency) {
            getExchangeRates();
            return list.stream()
                    .filter(it -> it.getCcy() == currency)
                    .filter(it -> it.getBase_ccy() == Currency.UAH)
                    .mapToDouble(PrivatBankCurrencyItem::getSale)
                    .findFirst()
                    .orElse(-1d);
        }

}
