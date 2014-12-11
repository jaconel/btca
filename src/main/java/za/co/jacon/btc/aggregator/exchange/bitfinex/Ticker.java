package za.co.jacon.btc.aggregator.exchange.bitfinex;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Representation of the ticker data received from the Bitfinex exhange's api.
 */
public class Ticker {

    private final BigDecimal averagePrice;
    private final BigDecimal bid;
    private final BigDecimal ask;
    private final BigDecimal lastPrice;
    private final BigDecimal lowestPrice;
    private final BigDecimal highestPrice;
    private final BigDecimal volume;
    private final BigDecimal timestamp;

    public Ticker(
        @JsonProperty("mid") final BigDecimal averagePrice,
        @JsonProperty("bid") final BigDecimal bid,
        @JsonProperty("ask") final BigDecimal ask,
        @JsonProperty("last_price") final BigDecimal lastPrice,
        @JsonProperty("low") final BigDecimal lowestPrice,
        @JsonProperty("high") final BigDecimal highestPrice,
        @JsonProperty("volume") final BigDecimal volume,
        @JsonProperty("timestamp") final BigDecimal timestamp) {

        this.averagePrice = averagePrice;
        this.bid =bid;
        this.ask = ask;
        this.lastPrice = lastPrice;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    /**
     * Retrieve the timestamp associated with the object.
     * @return timestamp
     */
    public BigDecimal getTimestamp() {
        return timestamp;
    }

    /**
     * Retrieve the average price over the last 24hours.
     * @return average price over 24 hours
     */
    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    /**
     * Return the innermost bid.
     * @return innermost bid
     */
    public BigDecimal getBid() {
        return bid;
    }

    /**
     * Retrieve the innermost ask.
     * @return innermost ask
     */
    public BigDecimal getAsk() {
        return ask;
    }

    /**
     * Retrieve the price at which the last order executed.
     * @return the latest price
     */
    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    /**
     * Lowest trade price of the last 24 hours.
     * @return lowest trade price
     */
    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    /**
     * Highest trade price of the last 24 hours.
     * @return highest trade price
     */
    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    /**
     * Trading volume of the last 24 hours.
     * @return trading volume
     */
    public BigDecimal getVolume() {
        return volume;
    }
}
