package za.co.jacon.btca.exchange.bitstamp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Represents a transaction as we receive it from the bitstamp api.
 */
public class TransactionVO extends za.co.jacon.btca.model.TransactionVO {

    /**
     * Class constructor.
     * @param timestamp transaction timestamp. eg. '1422546710'
     * @param price the transaction price. eg. 231.97
     * @param amount the transaction amount. eg. 0.24608565
     */
    @JsonCreator
    public TransactionVO(
            @JsonProperty("date") final long timestamp,
            @JsonProperty("price") final BigDecimal price,
            @JsonProperty("amount") final BigDecimal amount) {

        super(price, amount, timestamp);
    }
}
