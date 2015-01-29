package za.co.jacon.btc.aggregator.model;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Object that represents a single transaction returned from any btc exchange api.
 */
public class TransactionVO {

    private final BigDecimal price;
    private final long timestamp;
    private final long transactionId;

    /**
     * Class constructor allows for value injections.
     * @param price
     * @param timestamp
     * @param transactionId
     */
    public TransactionVO(final BigDecimal price, final long timestamp, final long transactionId) {
        this.price = price;
        this.timestamp = timestamp;
        this.transactionId = transactionId;
    }

    /**
     * Override the default to string method to provide a better representation of the class.
     * @return the object represented as a string
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, RecursiveToStringStyle.SHORT_PREFIX_STYLE);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getTransactionId() {
        return transactionId;
    }
}
