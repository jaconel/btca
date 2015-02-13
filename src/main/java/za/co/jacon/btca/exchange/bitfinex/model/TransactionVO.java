package za.co.jacon.btca.exchange.bitfinex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Object that represents a single transaction returned from any btc exchange api.
 */
public class TransactionVO extends za.co.jacon.btca.model.TransactionVO {

    /**
     * Class constructor allows for value injections.
     * @param price the transaction price in BTC
     * @param timestamp the transaction timestamp
     * @param amount the transaction amount.
     */
    @JsonCreator
    public TransactionVO(
            @JsonProperty("price") final BigDecimal price,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("amount") final BigDecimal amount) {

        super(price, amount, timestamp);
    }
}
