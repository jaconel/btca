package za.co.jacon.btc.aggregator.exchange.btx;

import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.exchange.accumulator.Accumulator;
import za.co.jacon.btc.aggregator.exchange.accumulator.PollingAccumulator;

/**
 * Accumulator for the BTX exchange.
 */
public class BitXAccumulator extends PollingAccumulator implements Accumulator, Runnable {

    /**
     * Logger instance. Makes use of log4j logger factory to instantiate an instance specific for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(BitXAccumulator.class);

    /**
     * The bitx api implementation.
     *
     * Injected via the class constructor.
     */
    protected final BitXApi api;

    /**
     * Class constructor.
     * @param api the bitx api implementation.
     */
    public BitXAccumulator(BitXApi api, int pollDelay) {
        super(pollDelay);

        LOGGER.debug("Initiating " + BitXAccumulator.class + " with delay of " + pollDelay + " seconds.");

        this.api = api;
    }

    /**
     * Runnable method.
     *
     * Get's executed by the scheduler in it's own thread. Is responsible for the retrieval of the ticker information
     * from the bitx api.ca
     */
    @Override
    public void run() {
        Ticker ticker = api.ticker();
        if (ticker != null) {
            LOGGER.info(ticker.getAsk());
        }
    }
}
