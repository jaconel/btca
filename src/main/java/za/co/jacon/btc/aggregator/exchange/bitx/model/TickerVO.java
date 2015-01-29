package za.co.jacon.btc.aggregator.exchange.bitx.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * BitX ticker object.
 */
public class TickerVO {

    protected BigDecimal ask;
    protected BigDecimal bid;
    protected long timestamp;
    protected BigDecimal lastTrade;
    protected Double rolling24HourVolume;
    protected String pair;

    public TickerVO(
            @JsonProperty("ask") BigDecimal ask,
            @JsonProperty("bid") BigDecimal bid,
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("last_trade") BigDecimal lastTrade,
            @JsonProperty("rolling_24_hour_volume") Double rolling24HourVolume,
            @JsonProperty("pair") String pair) {

        this.ask = ask;
        this.bid = bid;
        this.timestamp = timestamp;
        this.lastTrade = lastTrade;
        this.rolling24HourVolume = rolling24HourVolume;
        this.pair = pair;
    }

    /**
     * Returns the current asking price at the time of the tick.
     * @return asking price
     */
    public BigDecimal getAsk() {
        return ask;
    }

    /**
     * Returns the current bidding price at time of tick.
     * @return the bid price
     */
    public BigDecimal getBid() {
        return bid;
    }

    /**
     * Returns the tick timestamp.
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the last trade made right before the tick.
     * @return the last trade
     */
    public BigDecimal getLastTrade() {
        return lastTrade;
    }

    /**
     * Returns the current rolling 24 hour volume.
     *
     * @return 24 hour trading volume
     */
    public Double getRolling24HourVolume() {
        return rolling24HourVolume;
    }

    /**
     * Retrieve the pair information. This represents the currencies being compared in the ticker.
     * @return the pair information
     */
    public String getPair() {
        return pair;
    }
}
