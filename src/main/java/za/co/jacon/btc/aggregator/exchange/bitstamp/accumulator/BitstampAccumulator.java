package za.co.jacon.btc.aggregator.exchange.bitstamp.accumulator;

import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.accumulator.PollingAccumulator;
import za.co.jacon.btc.aggregator.exchange.bitstamp.api.BitstampApi;
import za.co.jacon.btc.aggregator.exchange.bitstamp.model.TransactionVO;

/**
 * The bitstamp accumulator responsible for polling the http api.
 */
public class BitstampAccumulator extends PollingAccumulator {

    private final Logger LOGGER = Logger.getLogger(BitstampAccumulator.class);

    protected final BitstampApi api;

    /**
     * Class constructor.
     *
     * Sets up the accumulator to start accumulating information. The accumulator is not started on construct.
     * @param distributor the message distributor
     * @param api the bitnex api implementation
     * @param pollDelay how often to poll the api for the latest information.
     */
    public BitstampAccumulator(final Distributor distributor, final BitstampApi api, final int pollDelay) {
        super(distributor, pollDelay);
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
            distributor.distribute(transaction, "bitstamp");
        }
    }
}
