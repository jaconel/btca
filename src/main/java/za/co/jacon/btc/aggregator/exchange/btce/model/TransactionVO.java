package za.co.jacon.btc.aggregator.exchange.btce.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Model object to represent a single transaction returned by the exchange's api..
 */
public class TransactionVO extends za.co.jacon.btc.aggregator.model.TransactionVO {

    /**
     * Class constructor.
     * @param price the price per btc for the transaction
     * @param amount the amount of btc for the transaction
     * @param timestamp the timestamp for the transaction
     */
    @JsonCreator
    public TransactionVO(
            @JsonProperty("price") final BigDecimal price,
            @JsonProperty("amount") final BigDecimal amount,
            @JsonProperty("timestamp") final long timestamp) {

        super(price, amount, timestamp);
    }
}
