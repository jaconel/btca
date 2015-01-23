package za.co.jacon.btc.aggregator.exchange.accumulator;

import za.co.jacon.btc.aggregator.distributor.Distributor;

/**
 * The abstract accumulator.
 */
public abstract class AbstractAccumulator implements Accumulator {

    protected final Distributor distributor;

    /**
     * Class constructor.
     * @param distributor the message distributor.
     */
    public AbstractAccumulator(Distributor distributor) {
        this.distributor = distributor;
    }
}
