package za.co.jacon.btc.aggregator.exchange.bitx.model;

import java.math.BigDecimal;

/**
 * Model representing a transaction on the BitX exchange.
 */
public class TransactionVO {

    private final long timestamp;
    private final BigDecimal volume;
    private final BigDecimal price;

    public TransactionVO(long timestamp, BigDecimal volume, BigDecimal price) {
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
