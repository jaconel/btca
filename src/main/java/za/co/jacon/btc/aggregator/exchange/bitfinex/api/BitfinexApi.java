package za.co.jacon.btc.aggregator.exchange.bitfinex.api;

import za.co.jacon.btc.aggregator.exchange.bitfinex.model.TickerVO;
import za.co.jacon.btc.aggregator.exchange.bitfinex.model.TransactionVO;


import java.util.List;

/**
 * Interface defining the api to the bitfinex exchange.
 */
public interface BitfinexApi {

    /**
     * Used to retrieve the ticker information from the exchange.
     * @return the ticker information
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
