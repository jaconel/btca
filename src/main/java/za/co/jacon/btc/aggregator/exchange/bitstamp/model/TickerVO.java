package za.co.jacon.btc.aggregator.exchange.bitstamp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * POJO representing the ticker data received from bitstamp.
 */
public class TickerVO {

    private final BigDecimal price;
    private final BigDecimal amount;
    private final BigDecimal id;

    /**
     * Class constructor.
     *
     * @param price the trade price
     * @param amount the trade amount
     * @param id the transaction id
     */
    public TickerVO(
        @JsonProperty("price") final BigDecimal price,
        @JsonProperty("amount") final BigDecimal amount,
        @JsonProperty("id") final long id) {

        this.price = price;
        this.amount = amount;
        this.id = amount;

    }

    /**
     * Return the trade price
     * @return trade price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Return the trade amount.
     * @return amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Return the trade id.
     * @return the id
     */
    public BigDecimal getId() {
        return id;
    }
}
