package za.co.jacon.btc.aggregator.exchange.bitx.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Model representing a transaction on the BitX exchange.
 */
public class TransactionVO extends za.co.jacon.btc.aggregator.model.TransactionVO {

    /**
     * Class constructor.
     *
     * Adapts the data response from the exchange into a known data format.
     * @param timestamp the transaction timestamp
     * @param volume the volume for the transaction
     * @param price the price per btc for the transaction
     */
    @JsonCreator
    public TransactionVO(
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("volume") BigDecimal volume,
            @JsonProperty("price") BigDecimal price) {

        super(price, volume, timestamp);
    }
}
