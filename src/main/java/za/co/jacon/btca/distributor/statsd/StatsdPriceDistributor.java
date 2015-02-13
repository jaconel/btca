package za.co.jacon.btca.distributor.statsd;

import com.timgroup.statsd.StatsDClient;
import za.co.jacon.btca.model.TransactionVO;

/**
 * A statsd distributor responsible for distributing pricing details to the statsd/graphite setup.
 */
public class StatsdPriceDistributor extends StatsdDistributor<TransactionVO> {

    /**
     * Class constructor.
     * @param statsDClient the statsd client.
     */
    public StatsdPriceDistributor(StatsDClient statsDClient) {
        super(statsDClient);
    }

    /**
     * Records the latest transaction's price, per exchange in the statsD setup.
     *
     * @param transactionVO model representing the latest transaction
     * @param exchange the exchange to which the transaction belongs.
     */
    @Override
    public void distribute(TransactionVO transactionVO, String exchange) {
        this.statsDClient.gauge(exchange, transactionVO.getPrice().doubleValue());
    }
}
