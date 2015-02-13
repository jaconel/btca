package za.co.jacon.btca.model;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Object that represents a single transaction normalized for all supported exchanges.
 */
public class TransactionVO {

    private final BigDecimal price;
    private final BigDecimal qty;
    private final long timestamp;
    private final long accumulationTimestamp;


    /**
     * Class constructor allows for value injections.
     * @param price
     * @param timestamp
     */
    public TransactionVO(
            final BigDecimal price,
            final BigDecimal qty,
            final long timestamp) {

        this.price = price;
        this.qty = qty;
        this.timestamp = timestamp;

        this.accumulationTimestamp = System.currentTimeMillis() / 1000L;
    }

    /**
     * Returns the transaction price.
     * @return the transaction price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Returns the timestamp for the transaction.
     * @return
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the transaction quantity.
     * @return the transaction qty
     */
    public BigDecimal getQty() {
        return qty;
    }

    /**
     * The time at which the data was accumulated.
     * @return the accumulation time
     */
    public long getAccumulationTimestamp() {
        return accumulationTimestamp;
    }

    /**
     * Override the default to string method to provide a better representation of the class.
     * @return the object represented as a string
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, RecursiveToStringStyle.SHORT_PREFIX_STYLE);
    }

}
