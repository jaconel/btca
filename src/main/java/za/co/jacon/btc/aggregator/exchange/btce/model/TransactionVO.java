package za.co.jacon.btc.aggregator.exchange.btce.model;

import java.math.BigDecimal;

/**
 * Model object to represent a single transaction returned by the exchange's api..
 */
public class TransactionVO {

    private final BigDecimal price;
    private final BigDecimal amount;
    private final long tid;
    private final long timestamp;

    public TransactionVO(
            final BigDecimal price, final BigDecimal amount, final long tid, final long timestamp) {

        this.price = price;
        this.amount = amount;
        this.tid = tid;
        this.timestamp = timestamp;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public long getTid() {
        return tid;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
