package za.co.jacon.btc.aggregator.exchange.accumulator;

import za.co.jacon.btc.aggregator.exchange.distributor.Distributor;

/**
 * Interface defining the rules for an accumulator.
 */
public interface Accumulator {

    public void start(Distributor distributor);
    public void stop();
}
