package za.co.jacon.btc.aggregator.exchange.btce.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Represents the ticker data returned by the btce exchange's api.
 */
public class TickerVO {

    private final BigDecimal highestPrice;
    private final BigDecimal lowestPrice;
    private final BigDecimal averagePrice;
    private final BigDecimal tradeVolume;
    private final BigDecimal tradeVolumeInCurrency;
    private final BigDecimal lastPrice;
    private final BigDecimal buyPrice;
    private final BigDecimal sellPrice;
    private final BigDecimal timestamp;

    /**
     * Class constructor
     * @param highestPrice the highest price over the last 24 hours
     * @param lowestPrice the lowest price over the last 24 hours
     * @param averagePrice the average price over the last 24 hours
     * @param tradeVolume the trade volume in the last 24 hours
     * @param tradeVolumeInCurrency the tade volume in the last 24 hours in the requested currency
     * @param lastPrice the last trade's price.
     * @param buyPrice the last bid price.
     * @param sellPrice the last ask price.
     * @param timestamp the timestamp
     */
    public TickerVO(
            @JsonProperty("high") final BigDecimal highestPrice,
            @JsonProperty("low") final BigDecimal lowestPrice,
            @JsonProperty("avg") final BigDecimal averagePrice,
            @JsonProperty("vol") final BigDecimal tradeVolume,
            @JsonProperty("vol_cur") final BigDecimal tradeVolumeInCurrency,
            @JsonProperty("last") final BigDecimal lastPrice,
            @JsonProperty("buy") final BigDecimal buyPrice,
            @JsonProperty("sell") final BigDecimal sellPrice,
            @JsonProperty("updated") final BigDecimal timestamp) {

        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.averagePrice = averagePrice;
        this.tradeVolume = tradeVolume;
        this.tradeVolumeInCurrency = tradeVolumeInCurrency;
        this.lastPrice = lastPrice;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.timestamp = timestamp;
    }

    /**
     * Retrieve the highest price achieved in the last 24 hours.
     * @return the highest price
     */
    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    /**
     * Retrieve the lowest price reached in the last 24 hours.
     * @return the lowest price
     */
    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    /**
     * Retrieve the average price over the last 24 hours.
     * @return the average price
     */
    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    /**
     * Retrieve the average trade volume over the last 24 hours.
     * @return the average trade volume
     */
    public BigDecimal getTradeVolume() {
        return tradeVolume;
    }

    /**
     * Retrieve the average trade volume over the last 24 hours in the currency requested.
     * @return the average trade volume in currency
     */
    public BigDecimal getTradeVolumeInCurrency() {
        return tradeVolumeInCurrency;
    }

    /**
     * Retrieve the price of the last trade made.
     * @return the price of the last trade made
     */
    public BigDecimal getLastPrice() {
        return lastPrice;
    }

    /**
     * Retrieve buy price
     * @return the buy price
     */
    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    /**
     * Retrieve the sell price.
     * @return the sell price
     */
    public BigDecimal getSellPirce() {
        return sellPrice;
    }

    /**
     * Retrieve the timestamp.
     * @return the timestamp
     */
    public BigDecimal getTimestamp() {
        return timestamp;
    }
}
