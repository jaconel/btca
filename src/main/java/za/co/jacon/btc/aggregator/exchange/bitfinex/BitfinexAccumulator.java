package za.co.jacon.btc.aggregator.exchange.bitfinex;

import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.exchange.accumulator.PollingAccumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.exchange.bitfinex.vo.TickerVO;
import za.co.jacon.btc.aggregator.exchange.bitfinex.vo.TransactionVO;

import java.util.List;

/**
 * The Bitfinex accumulator.
 *
 * Accumulates information from the Bitfinex exchange and publishes it to the distributors registered in the system.
 */
public class BitfinexAccumulator extends PollingAccumulator {

    private final Logger LOGGER = Logger.getLogger(BitfinexAccumulator.class);

    protected final BitfinexApi api;

    /**
     * Class constructor.
     *
     * Sets up the accumulator to start accumulating information. The accumulator is not started on construct.
     * @param distributor the message distributor
     * @param api the bitnex api implementation
     * @param pollDelay how often to poll the api for the latest information.
     */
    public BitfinexAccumulator(final Distributor distributor, final BitfinexApi api, final int pollDelay) {
        super(distributor, pollDelay);

        LOGGER.debug("Initiating " + BitfinexAccumulator.class + " with delay " + pollDelay);

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
        /*TickerVO tickerVO = api.ticker();
        if (tickerVO != null) {
            this.distributor.distribute(tickerVO, "bitfinex");
        }*/

        TransactionVO transaction = api.getLatestTransaction();

        LOGGER.info("Latest transaction for bitfinex was for " + transaction.getPrice());
    }
}
