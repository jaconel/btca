package za.co.jacon.btca.accumulator;

import za.co.jacon.btca.distributor.Distributor;

import java.util.List;

/**
 * The abstract accumulator.
 */
public abstract class AbstractAccumulator<T> implements Accumulator {

    protected final List<Distributor<T>> distributors;

    /**
     * Class constructor.
     * @param distributors the message distributors.
     */
    public AbstractAccumulator(List<Distributor<T>>  distributors) {
        this.distributors = distributors;
    }
}
