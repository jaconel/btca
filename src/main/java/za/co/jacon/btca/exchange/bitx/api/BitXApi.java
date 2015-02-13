package za.co.jacon.btca.exchange.bitx.api;

import za.co.jacon.btca.exchange.bitx.model.TickerVO;
import za.co.jacon.btca.exchange.bitx.model.TransactionVO;

import java.util.List;

/**
 * API for integrating with the BitX exchange.
 */
public interface BitXApi {

    /**
     * Retrieves the ticker information from the exchange.
     * @return the ticker info at the current time
     */
    public TickerVO ticker();

    /**
     * Retrieves a list of the latest transactions from the exchange.
     * @return a list of the latest transactions at the current time
     */
    public List<TransactionVO> getListOfLatestTransactions();

    /**
     * Retrieves the latest transaction from the exchange.
     * @return the latest transaction at the current time.
     */
    public TransactionVO getLatestTransaction();
}
