package za.co.jacon.btc.aggregator.exchange.bitfinex;

import za.co.jacon.btc.aggregator.exchange.bitfinex.vo.TickerVO;
import za.co.jacon.btc.aggregator.exchange.bitfinex.vo.TransactionVO;

import java.util.List;

/**
 * Interface defining the api to the bitfinex exchange.
 */
public interface BitfinexApi {
    public TickerVO ticker();
    public List<TransactionVO> getListOfLatestTransactions();
    public TransactionVO getLatestTransaction();
}
