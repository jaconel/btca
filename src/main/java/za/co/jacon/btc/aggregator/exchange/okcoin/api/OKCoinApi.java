package za.co.jacon.btc.aggregator.exchange.okcoin.api;

import za.co.jacon.btc.aggregator.exchange.okcoin.model.TransactionVO;

import java.util.List;

/**
 * Define the api for interacting with the OKCoin exchange.
 */
public interface OKCoinApi {

    /**
     * Retrieves a list of the latest transactions from the exchange.
     * @return a list of transaction objects
     */
    public List<TransactionVO> getListOfLatestTransactions();

    /**
     * Retrieves the latest transaction from the exchange.
     * @return the latest transaction object
     */
    public TransactionVO getLatestTransaction();
}
