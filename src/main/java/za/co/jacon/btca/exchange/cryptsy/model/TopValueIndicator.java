package za.co.jacon.btca.exchange.cryptsy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Represents the top buy or top sell indicated in the cryptsy ticker.
 */
public class TopValueIndicator {

    protected BigDecimal price;
    protected BigDecimal quantity;

    /**
     * Constructs a new TopBuy or TopSell indicator.
     * @param price
     * @param quantity
     */
    public TopValueIndicator(
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("quantity") BigDecimal  quantity) {

        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Returns the price.
     *
     * @return the price as a BigDecimal
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * Return the quantity
     * @return the quantity as a BigDecimal
     */
    public BigDecimal getQuantity() {
        return this.quantity;
    }
}
