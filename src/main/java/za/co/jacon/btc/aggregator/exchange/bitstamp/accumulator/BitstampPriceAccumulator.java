package za.co.jacon.btc.aggregator.exchange.bitstamp.accumulator;

import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.accumulator.PollingAccumulator;
import za.co.jacon.btc.aggregator.exchange.bitstamp.api.BitstampApi;
import za.co.jacon.btc.aggregator.model.TransactionVO;


import java.util.List;

/**
 * The bitstamp accumulator responsible for polling the http api.
 */
public class BitstampPriceAccumulator extends PollingAccumulator<TransactionVO> {

    /**
     * Instance of the logger.
     *
     * Allows for application level loggin.
     */
    private final Logger LOGGER = Logger.getLogger(BitstampPriceAccumulator.class);

    /**
     * Instance of the bitstamp api implementation.
     */
    protected final BitstampApi api;

    /**
     * Reference key for this specific exchange.
     */
    public static final String EXCHANGE_REFERENCE = "bitstamp";

    /**
     * Class constructor.
     *
     * Sets up the accumulator to start accumulating information. The accumulator is not started on construct.
     * @param distributors the message distributors
     * @param api the bitnex api implementation
     * @param pollDelay how often to poll the api for the latest information.
     */
    public BitstampPriceAccumulator(final List<Distributor<TransactionVO>>  distributors, final BitstampApi api, final int pollDelay) {
        super(distributors, pollDelay);
        this.api = api;
    }
    /**
     * The run method gets called by the scheduled task responsible for polling the bitfinex api. It is responsible
     * for calling the api and converting the response to the correct POJO.
     *
     * It is also responsible to pass the correct value to the distributor
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
