package za.co.jacon.btc.aggregator.exchange.bitfinex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

/**
 * Object that represents a single transaction returned from any btc exchange api.
 */
public class TransactionVO {

    private final BigDecimal price;
    private final BigDecimal amount;
    private final long timestamp;
    private final long tid;
    private String test;

    /**
     * Class constructor allows for value injections.
     * @param price
     * @param timestamp
     * @param tid
     * @param amount
     */
    @JsonCreator
    public TransactionVO(
            @JsonProperty("price") final BigDecimal price,
            @JsonProperty("timestamp") final long timestamp,
            @JsonProperty("tid") final long tid,
            @JsonProperty("amount") final BigDecimal amount,
            @JsonProperty("tewst") final String test
            ) {

        this.price = price;
        this.amount = amount;
        this.timestamp = timestamp;
        this.tid = tid;

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

    public long getTid() {
        return tid;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
