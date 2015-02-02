package za.co.jacon.btc.aggregator.config;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import za.co.jacon.btc.aggregator.distributor.AMQPDistributor;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.distributor.StatsdDistributor;

/**
 * Configuration related to the setup of any/all distributors.
 */
@Configuration
public class DistributorConfig {

    /**
     * Configure a distributor which will distribute data via a amqp message queue.
     * @param amqpTemplate the amqp template for interacting with the message queue.
     * @return the configured distributor
     */
    @Bean
    public Distributor AMQPDistributor(final AmqpTemplate amqpTemplate) {
        return new AMQPDistributor(amqpTemplate);
    }

    /**
     * Configure a distributor which will make use of statsd to distribute metric related to the data.
     * @param statsDClient the configures statsd client.
     * @return the configured distributor.
     */
    @Bean
    public Distributor statsdDistributor(final StatsDClient statsDClient) {
        return new StatsdDistributor(statsDClient);
    }

    /**
     * Returns a non-blocking statsd client.
     * @return the statsd client
     */
    @Bean
    public StatsDClient statsDClient(Environment environment) {
        final String statsdPrefix = environment.getRequiredProperty("statsd.prefix");
        final String statsdHost = environment.getRequiredProperty("statsd.host");
        final Integer statsdPort = environment.getRequiredProperty("statsd.port", Integer.class);

        return new NonBlockingStatsDClient(statsdPrefix, statsdHost, statsdPort);
    }
}
