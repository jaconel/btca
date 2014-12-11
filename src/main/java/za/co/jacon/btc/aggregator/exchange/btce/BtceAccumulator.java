package za.co.jacon.btc.aggregator.exchange.btce;

import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.exchange.accumulator.PollingAccumulator;

/**
 * The BTC-e accumulator.
 *
 * Responsible for accumulating information from the BTC-e exchange.
 */
public class BtceAccumulator extends PollingAccumulator {

    private static final Logger LOGGER = Logger.getLogger(BtceAccumulator.class);
    private final BtceApi api;

    /**
     * Class constructor.
     *
     * Allows the injection of the BTCe api implementation.
     *
     * @param api the api implementation
     * @param pollDelay how often to poll the exchange for ticker data.
     */
    public BtceAccumulator(final BtceApi api, final int pollDelay) {
        super(pollDelay);

        LOGGER.debug("Initiating " + BtceAccumulator.class + " with delay of " + pollDelay + " seconds.");

        this.api = api;
    }

    /**
     * Makes use of the BTC-e api to retrieve the
     */
    @Override
    public void run() {
        Ticker ticker = api.ticker();
        if (ticker != null) {
            LOGGER.info(ticker.getLastPrice());
        }
    }
}
