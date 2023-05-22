package currency.mono.dto;

import currency.Currency;
import lombok.Data;

@Data
public class MonoBankCurrencyItem {
    private int currencyCodeA;
    private int currencyCodeB;
    private long date;
    private double rateBuy;
    private double rateCross;
    private double rateSell;
    private Currency baseCurrency;
    private Currency currency;



    public Currency getCurrency(){
        if(currencyCodeA == 840){
            currency = Currency.USD;
        }
        if(currencyCodeA == 978){
            currency = Currency.EUR;
        }
        return currency;
    }

    public Currency getBaseCurrency(){

        if(currencyCodeB == 980){
            baseCurrency = Currency.UAH;
        }
        return baseCurrency;
    }
}
