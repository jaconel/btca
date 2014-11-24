package za.co.jacon.btc.aggregator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import za.co.jacon.btc.aggregator.exchange.accumulator.Accumulator;
import za.co.jacon.btc.aggregator.exchange.accumulator.AccumulatorCoordinator;
import za.co.jacon.btc.aggregator.exchange.cryptsy.CryptsyAccumulator;
import za.co.jacon.btc.aggregator.exchange.distributor.AMQPDistributor;
import za.co.jacon.btc.aggregator.exchange.distributor.Distributor;

import java.util.List;

/**
 * Context configuration for setting up spring dependency injection.
 */
@Configuration
public class ContextConfig {

    @Bean
    public Accumulator cryptsyAccumulator() {
        return new CryptsyAccumulator(new Pusher("cb65d0a7a72cd94adf1f"), "ticker.2");
    }

    @Bean
    public Distributor getAMQPDistributor() {
        return new AMQPDistributor();
    }

    @Bean
    public AccumulatorCoordinator getCoordinator(List<Accumulator> accumulators, Distributor distributor) {
        return new AccumulatorCoordinator(accumulators, distributor);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
