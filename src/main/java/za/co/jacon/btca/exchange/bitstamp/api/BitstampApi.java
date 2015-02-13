package za.co.jacon.btca.exchange.bitstamp.api;

import za.co.jacon.btca.exchange.bitstamp.model.TransactionVO;

import java.util.List;

/**
 * Interface defining a bitstamp api implementation.
 */
public interface BitstampApi {

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
