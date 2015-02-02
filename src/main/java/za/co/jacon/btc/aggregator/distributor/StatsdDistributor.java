package za.co.jacon.btc.aggregator.distributor;

import com.timgroup.statsd.StatsDClient;

/**
 * Statsd distributor.
 *
 * Distributes information to a statsd endpoint.
 */
public class StatsdDistributor implements Distributor {

    /**
     * The statsD client used for communication with the statsd server.
     */
    private final StatsDClient statsDClient;

    /**
     * Class constructor.
     *
     * @param statsDClient the statsd client.
     */
    public StatsdDistributor(final StatsDClient statsDClient) {
        this.statsDClient = statsDClient;
    }

    @Override
    public void distribute(Object dataObject, String exchange) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
