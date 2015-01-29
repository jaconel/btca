package za.co.jacon.btc.aggregator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import za.co.jacon.btc.aggregator.accumulator.Accumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.exchange.bitfinex.accumulator.BitfinexAccumulator;
import za.co.jacon.btc.aggregator.exchange.bitfinex.api.BitfinexApi;
import za.co.jacon.btc.aggregator.exchange.bitstamp.accumulator.BitstampAccumulator;
import za.co.jacon.btc.aggregator.exchange.bitstamp.api.BitstampApi;

/**
 * Set's up the Accumulator in the spring dependancy injection container.
 */
@Configuration
public class AccumulatorConfig {

    /**
     * Configures and set's up the bitstamp accumulator.
     *
     * @param environment the spring environment
     * @param bitstampApi the configure bitstamp api
     * @param distributor the configured distributor
     *
     * @return the configured bitstamp accumulator.
     */
    @Bean
    public Accumulator bitstampAccumulator(final Environment environment, final BitstampApi bitstampApi, final Distributor distributor) {
        final int pollDelay = environment.getRequiredProperty("exchange.bitstamp.accumulator.poll_delay_in_seconds", Integer.class);

        return new BitstampAccumulator(distributor, bitstampApi, pollDelay);
    }

    /**
     * Configures and set's up the bitfinex accumulator.
     *
     * @param environment the spring environment
     * @param bitfinexApi the configure bitstamp api
     * @param distributor the configured distributor
     *
     * @return the configured bitstamp accumulator.
     */
    @Bean
    public Accumulator bitfinexAccumulator(final Environment environment, final BitfinexApi bitfinexApi, final Distributor distributor) {
        final int pollDelay = environment.getRequiredProperty("exchange.bitfinex.accumulator.poll_delay_in_seconds", Integer.class);
        return new BitfinexAccumulator(distributor, bitfinexApi, pollDelay);
    }

     /* @Bean
    public Accumulator cryptsyAccumulator(final Environment environment, final ObjectMapper mapper, final Distributor distributor) {
        final String pusherKey = environment.getRequiredProperty("exchange.crypsty.accumulator.pusher_key", String.class);
        final String pusherChannel = environment.getRequiredProperty("exchange.crypsty.accumulator.pusher_channel", String.class);
        final String pusherEvent = environment.getRequiredProperty("exchange.crypsty.accumulator.pusher_event", String.class);

        return new CryptsyAccumulator(distributor, new Pusher(pusherKey), pusherChannel, pusherEvent, mapper);
    }

    @Bean
    public Accumulator bitXAccumulator(final Environment environment, final BitXApi bitXApi, final Distributor distributor) {
        final int pollDelay = environment.getRequiredProperty("exchange.bitx.accumulator.poll_delay_in_seconds", Integer.class);
        return new BitXAccumulator(distributor, bitXApi, pollDelay);
    }

    @Bean
    public Accumulator btceAccumulator(final Environment environment, final BtceApi api, final Distributor distributor) {
        final int pollDelay = environment.getRequiredProperty("exchange.btce.accumulator.poll_delay_in_seconds", Integer.class);
        return new BtceAccumulator(distributor, api, pollDelay);
    }
    */
}
