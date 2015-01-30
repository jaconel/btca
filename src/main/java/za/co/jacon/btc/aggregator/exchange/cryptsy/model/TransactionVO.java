package za.co.jacon.btc.aggregator.exchange.cryptsy.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Represents a single transaction on the cryptsy exchange.
 */
public class TransactionVO extends za.co.jacon.btc.aggregator.model.TransactionVO {

    /**
     * Class constructor.
     *
     * @param timestamp the timestamp associated with the transaction.
     * @param price the price associated with the transaction. (price of single btc)
     * @param quantity the quantity of btc for the transaction
     */
    @JsonCreator
    public TransactionVO(
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("quantity") BigDecimal quantity) {

        super(price, quantity, timestamp);
    }
}
