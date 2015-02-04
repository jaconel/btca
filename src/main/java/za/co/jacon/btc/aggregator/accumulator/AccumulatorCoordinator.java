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

    public AccumulatorCoordinator(List<Accumulator> accumulators) {
        this.accumulators = accumulators;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (Accumulator accumulator: this.accumulators) {
            accumulator.start();
        }
    }

    @Override
    public void destroy() throws Exception {
        for (Accumulator accumulator: this.accumulators) {
            accumulator.stop();
        }
    }
}
