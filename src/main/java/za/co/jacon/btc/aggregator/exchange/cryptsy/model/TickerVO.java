package za.co.jacon.btc.aggregator.exchange.cryptsy.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a single ticker result returned by cryptsy.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerVO {

    protected final TopValueIndicator topSell;
    protected final TopValueIndicator topBuy;
    protected final Long timestamp;
    protected final Integer marketId;

    /**
     * Class constructor.
     * @param timestamp the timestamp at which the ticked
     * @param marketId
     * @param topSell
     * @param topBuy
     */
    public TickerVO(
            @JsonProperty("timestamp") Long timestamp,
            @JsonProperty("marketid") Integer marketId,
            @JsonProperty("topsell") TopValueIndicator topSell,
            @JsonProperty("topbuy") TopValueIndicator topBuy) {

        this. timestamp = timestamp;
        this.marketId = marketId;
        this.topSell = topSell;
        this.topBuy = topBuy;
    }

    /**
     * Return the event timestamp
     * @return the event timestamp
     */
    public Long getTimestamp() {
        return this.timestamp;
    }

    /**
     * The event marketID
     * @return the market id
     */
    public Integer getMarketId() {
        return this.marketId;
    }

    /**
     * Return the top sell values.
     * @return top sell values
     */
    public TopValueIndicator getTopSell() {
        return this.topSell;
    }

    /**
     * Return the top buy values.
     * @return top buy values.
     */
    public TopValueIndicator getTopBuy() {
        return this.topBuy;
    }
}
