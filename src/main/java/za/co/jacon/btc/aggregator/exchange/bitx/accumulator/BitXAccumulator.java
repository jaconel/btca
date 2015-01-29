package za.co.jacon.btc.aggregator.exchange.bitx.accumulator;

import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.accumulator.PollingAccumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.exchange.bitx.api.BitXApi;
import za.co.jacon.btc.aggregator.exchange.bitx.model.TickerVO;

/**
 * Accumulator for the BTX exchange.
 */
public class BitXAccumulator extends PollingAccumulator {

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
     *
     * @param distributor the message distributor.
     * @param api the bitx api implementation.
     * @param pollDelay how often should we poll the exchange for data
     */
    public BitXAccumulator(final Distributor distributor, final BitXApi api, final int pollDelay) {
        super(distributor, pollDelay);

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
        TickerVO tickerVO = api.ticker();
        if (tickerVO != null) {
            this.distributor.distribute(tickerVO, "bitx");
        }
    }
}
