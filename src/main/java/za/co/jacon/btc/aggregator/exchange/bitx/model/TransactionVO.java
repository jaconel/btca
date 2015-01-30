package za.co.jacon.btc.aggregator.exchange.bitx.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Model representing a transaction on the BitX exchange.
 */
public class TransactionVO {

    private final long timestamp;
    private final BigDecimal volume;
    private final BigDecimal price;

    @JsonCreator
    public TransactionVO(
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("volume") BigDecimal volume,
            @JsonProperty("price") BigDecimal price) {

        this.timestamp = timestamp;
        this.volume = volume;
        this.price = price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
