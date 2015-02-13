package za.co.jacon.btca.exchange.btce.api;

import za.co.jacon.btca.exchange.btce.model.TransactionVO;
import za.co.jacon.btca.exchange.btce.model.TickerVO;

import java.util.List;

/**
 * Defines the interface to the BTC-e exchange.
 */
public interface BtceApi {

    /**
     * Retrieves the ticker data from the exchange.
     * @return the ticker data.
     */
    public TickerVO ticker();

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
