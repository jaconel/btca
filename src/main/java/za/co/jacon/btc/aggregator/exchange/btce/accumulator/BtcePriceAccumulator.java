package za.co.jacon.btc.aggregator.exchange.btce.accumulator;

import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.accumulator.PollingAccumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.exchange.btce.api.BtceApi;
import za.co.jacon.btc.aggregator.model.TransactionVO;

import java.util.List;

/**
 * The BTC-e accumulator.
 *
 * Responsible for accumulating information from the BTC-e exchange.
 */
public class BtcePriceAccumulator extends PollingAccumulator<TransactionVO> {

    /**
     * Instance of the application logger.
     */
    private static final Logger LOGGER = Logger.getLogger(BtcePriceAccumulator.class);

    /**
     * The exchange api.
     */
    private final BtceApi api;

    /**
     * The exchange reference value.
     */
    public static final String EXCHANGE_REFERENCE = "btce";

    /**
     * Class constructor.
     *
     * Allows the injection of the BTCe api implementation.
     *
     * @param distributors the message distributor
     * @param api the api implementation
     * @param pollDelay how often to poll the exchange for ticker data.
     */
    public BtcePriceAccumulator(final List<Distributor<TransactionVO>> distributors, final BtceApi api, final int pollDelay) {
        super(distributors, pollDelay);

        this.api = api;
    }

    /**
     * Makes use of the BTC-e api to retrieve the
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
