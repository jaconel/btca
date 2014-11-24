package za.co.jacon.btc.aggregator.exchange.cryptsy;

import java.util.Date;

/**
 * Represents a single ticker result returned by cryptsy.
 */
public class Ticker {

    protected final TopValueIndicator topSell;
    protected final TopValueIndicator topBuy;
    protected final Long timestamp;
    protected final Date dateTime;
    protected final Integer marketId;

    /**
     * Class constructor.
     * @param timestamp the timestamp at which the ticked
     * @param dateTime
     * @param marketId
     * @param topSell
     * @param topBuy
     */
    public Ticker(Long timestamp, Date dateTime, Integer marketId, TopValueIndicator topSell, TopValueIndicator topBuy) {
        this. timestamp = timestamp;
        this.dateTime = dateTime;
        this.marketId = marketId;
        this. topSell = topSell;
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
     * The event date as java Date object.
     * @return the event date
     */
    public Date getDateTime() {
        return this.dateTime;
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
