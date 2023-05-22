package currency.nbu.dto;
import lombok.Data;

@Data
public class NBUCurrencyItem {
    private String r030;
    private String txt;
    private double rate;
    private String cc;
    private String exchangedate;
}