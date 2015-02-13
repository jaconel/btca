package za.co.jacon.btca.distributor.statsd;

import com.timgroup.statsd.StatsDClient;
import za.co.jacon.btca.distributor.Distributor;

/**
 * Statsd distributor.
 *
 * Distributes information to a statsd endpoint.
 */
public abstract class StatsdDistributor<T> implements Distributor<T> {

    /**
     * The statsD client used for communication with the statsd server.
     */
    protected final StatsDClient statsDClient;

    /**
     * Class constructor.
     *
     * @param statsDClient the statsd client.
     */
    public StatsdDistributor(final StatsDClient statsDClient) {
        this.statsDClient = statsDClient;
    }
}
