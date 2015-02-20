package za.co.jacon.btca.exchange.bitfinex.accumulator;

import org.apache.log4j.Logger;
import za.co.jacon.btca.accumulator.PollingAccumulator;
import za.co.jacon.btca.distributor.Distributor;
import za.co.jacon.btca.exchange.bitfinex.api.BitfinexApi;
import za.co.jacon.btca.model.TransactionVO;

import java.util.List;

/**
 * The Bitfinex accumulator.
 *
 * Accumulates information from the Bitfinex exchange and publishes it to the distributors registered in the system.
 */
public class BitfinexPriceAccumulator extends PollingAccumulator<TransactionVO> {

    private final Logger LOGGER = Logger.getLogger(BitfinexPriceAccumulator.class);

    protected final BitfinexApi api;

    /**
     * Class constructor.
     *
     * Sets up the accumulator to start accumulating information. The accumulator is not started on construct.
     * @param distributors the message distributors
     * @param api the bitnex api implementation
     * @param pollDelay how often to poll the api for the latest information.
     */
    public BitfinexPriceAccumulator(final List<Distributor<TransactionVO>> distributors, final BitfinexApi api, final int pollDelay) {
        super(distributors, pollDelay);

        LOGGER.debug("Initiating " + BitfinexPriceAccumulator.class + " with delay " + pollDelay);

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
                distributor.distribute(transaction, "bitfinex");
            }
        }
    }
}
