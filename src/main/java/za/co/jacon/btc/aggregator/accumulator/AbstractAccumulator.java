package za.co.jacon.btc.aggregator.accumulator;

import za.co.jacon.btc.aggregator.distributor.Distributor;

import java.util.List;

/**
 * The abstract accumulator.
 */
public abstract class AbstractAccumulator implements Accumulator {

    protected final List<Distributor> distributors;

    /**
     * Class constructor.
     * @param distributors the message distributors.
     */
    public AbstractAccumulator(List<Distributor>  distributors) {
        this.distributors = distributors;
    }
}
