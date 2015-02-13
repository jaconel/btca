package za.co.jacon.btca.exchange.okcoin.accumulator;

import za.co.jacon.btca.accumulator.PollingAccumulator;
import za.co.jacon.btca.distributor.Distributor;
import za.co.jacon.btca.exchange.okcoin.api.OKCoinApi;
import za.co.jacon.btca.model.TransactionVO;

import java.util.List;

/**
 * Accumulator responsible for accumulating transaction data from the OKCoin exchange.
 */
public class OKCoinPriceAccumulator extends PollingAccumulator<TransactionVO> {

    /**
     * An instance of the okcoin api.
     */
    private final OKCoinApi api;

    /**
     * The exchange reference name.
     */
    public static final String EXCHANGE_REFERENCE = "okcoin";

    /**
     * Class constructor.
     *
     * Sets the polling delay for the scheduled task and injects the api.
     *
     * @param distributors list of event distributors.
     * @param pollDelay the polling delay
     */
    public OKCoinPriceAccumulator(List<Distributor<TransactionVO>> distributors, OKCoinApi api, int pollDelay) {
        super(distributors, pollDelay);
        this.api = api;
    }

    /**
     * Periodically calls the exchange to retrieve the latest transactions.
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
