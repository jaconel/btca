package za.co.jacon.btc.aggregator.accumulator;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import za.co.jacon.btc.aggregator.distributor.Distributor;

import java.util.List;

/**
 * Controls the initiation of the accumulators.
 */
public class AccumulatorCoordinator implements InitializingBean, DisposableBean {

    private final List<Accumulator> accumulators;
    private final Distributor distributor;

    public AccumulatorCoordinator(List<Accumulator> accumulators, Distributor distributor) {
        this.accumulators = accumulators;
        this.distributor = distributor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (Accumulator accumulator: this.accumulators) {
            accumulator.start(this.distributor);
        }
    }

    @Override
    public void destroy() throws Exception {
        for (Accumulator accumulator: this.accumulators) {
            accumulator.stop();
        }
    }
}
