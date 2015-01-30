package za.co.jacon.btc.aggregator.exchange.cryptsy.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Represents a single transaction on the cryptsy exchange.
 */
public class TransactionVO {

    private final long timestamp;
    private final BigDecimal price;
    private final BigDecimal quantity;
    private final BigDecimal total;

    /**
     * Class constructor.
     * @param timestamp the timestamp associated with the transaction.
     * @param price the price associated with the transaction. (price of single btc)
     * @param quantity the quantity of btc for the transaction
     * @param total the total cost of the transaction.
     */
    @JsonCreator
    public TransactionVO(
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("quantity") BigDecimal quantity,
            @JsonProperty("total") BigDecimal total) {

        this.timestamp = timestamp;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
