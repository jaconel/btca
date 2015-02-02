package za.co.jacon.btc.aggregator.exchange.bitx.accumulator;

import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.accumulator.PollingAccumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.exchange.bitx.api.BitXApi;
import za.co.jacon.btc.aggregator.exchange.bitx.model.TransactionVO;

import java.util.List;

/**
 * Accumulator for the BTX exchange.
 */
public class BitXPriceAccumulator extends PollingAccumulator {

    /**
     * Logger instance. Makes use of log4j logger factory to instantiate an instance specific for this class.
     */
    private static final Logger LOGGER = Logger.getLogger(BitXPriceAccumulator.class);

    /**
     * The bitx api implementation.
     *
     * Injected via the class constructor.
     */
    protected final BitXApi api;

    /**
     * The exchange reference.
     */
    public static final String EXCHANGE_REFERENCE = "bitx";

    /**
     * Class constructor.
     *
     * @param distributors the message distributor.
     * @param api the bitx api implementation.
     * @param pollDelay how often should we poll the exchange for data
     */
    public BitXPriceAccumulator(final List<Distributor> distributors, final BitXApi api, final int pollDelay) {
        super(distributors, pollDelay);
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
        TransactionVO transaction = api.getLatestTransaction();
        if (transaction != null) {
            for (Distributor distributor: distributors) {
                distributor.distribute(transaction, EXCHANGE_REFERENCE);
            }
        }
    }
}
