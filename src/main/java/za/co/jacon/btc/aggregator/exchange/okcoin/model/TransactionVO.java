package za.co.jacon.btc.aggregator.exchange.okcoin.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Represents a transaction in the OKCoin exchange.
 */
public class TransactionVO {

    private final BigDecimal amount;
    private final long timestamp;
    private final BigDecimal price;
    private final long tid;

    @JsonCreator
    public TransactionVO(
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("date_ms") long timestamp,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("tid") long tid) {

        this.amount = amount;
        this.timestamp = timestamp;
        this.price = price;
        this.tid = tid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getTid() {
        return tid;
    }
}
