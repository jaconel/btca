package za.co.jacon.btc.aggregator.exchange.btce.accumulator;

import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.accumulator.PollingAccumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.exchange.btce.api.BtceApi;
import za.co.jacon.btc.aggregator.exchange.btce.model.TransactionVO;

/**
 * The BTC-e accumulator.
 *
 * Responsible for accumulating information from the BTC-e exchange.
 */
public class BtcePriceAccumulator extends PollingAccumulator {

    private static final Logger LOGGER = Logger.getLogger(BtcePriceAccumulator.class);
    private final BtceApi api;

    /**
     * Class constructor.
     *
     * Allows the injection of the BTCe api implementation.
     *
     * @param distributor the message distributor
     * @param api the api implementation
     * @param pollDelay how often to poll the exchange for ticker data.
     */
    public BtcePriceAccumulator(final Distributor distributor, final BtceApi api, final int pollDelay) {
        super(distributor, pollDelay);

        LOGGER.debug("Initiating " + BtcePriceAccumulator.class + " with delay of " + pollDelay + " seconds.");

        this.api = api;
    }

    /**
     * Makes use of the BTC-e api to retrieve the
     */
    @Override
    public void run() {
        TransactionVO transaction = api.getLatestTransaction();
        if (transaction != null) {
            distributor.distribute(transaction, "btce");
        }
    }
}
