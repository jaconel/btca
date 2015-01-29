package za.co.jacon.btc.aggregator.exchange.bitstamp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Represents a transaction as we receive it from the bitstamp api.
 */
public class TransactionVO {

    private final long date;
    private final long tid;
    private final BigDecimal price;
    private final BigDecimal amount;

    /**
     * Class constructor.
     * @param date transaction timestamp. eg. '1422546710'
     * @param tid the transaction id. eg. 7417085
     * @param price the transaction price. eg. 231.97
     * @param amount the transaction amount. eg. 0.24608565
     */
    @JsonCreator
    public TransactionVO(
            @JsonProperty("date") final long date,
            @JsonProperty("tid") final long tid,
            @JsonProperty("price") final BigDecimal price,
            @JsonProperty("amount") final BigDecimal amount) {

        this.date = date;
        this.tid = tid;
        this.price = price;
        this.amount = amount;
    }

    /**
     * Retrieves the timestamp associated with the transaction.
     * @return the transaction timestamp.
     */
    public long getDate() {
        return date;
    }

    /**
     * Retrieves the transaction id.
     * @return the transaction id
     */
    public long getTid() {
        return tid;
    }

    /**
     * Retrieves the transaction price.
     * @return the price at which the transaction was done
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * The amount of btc traded in the transaction.
     * @return the amount of btc.
     */
    public BigDecimal getAmount() {
        return amount;
    }
}
