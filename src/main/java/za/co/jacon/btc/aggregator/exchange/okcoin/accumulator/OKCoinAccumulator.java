package za.co.jacon.btc.aggregator.exchange.okcoin.accumulator;

import za.co.jacon.btc.aggregator.accumulator.PollingAccumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.exchange.okcoin.api.OKCoinApi;
import za.co.jacon.btc.aggregator.exchange.okcoin.model.TransactionVO;

/**
 * Accumulator responsible for accumulating transaction data from the OKCoin exchange.
 */
public class OKCoinAccumulator extends PollingAccumulator {

    private final OKCoinApi api;

    /**
     * Class constructor.
     *
     * Sets the polling delay for the scheduled task and injects the api.
     *
     * @param pollDelay the polling delay
     */
    public OKCoinAccumulator(Distributor distributor, OKCoinApi api, int pollDelay) {
        super(distributor, pollDelay);
        this.api = api;
    }

    /**
     * Periodically calls the exchange to retrieve the latest transactions.
     */
    @Override
    public void run() {
        TransactionVO transaction = api.getLatestTransaction();
        if (transaction != null) {
            distributor.distribute(transaction, "okcoin");
        }
    }
}
