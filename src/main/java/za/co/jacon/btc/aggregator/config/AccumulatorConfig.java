package za.co.jacon.btc.aggregator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import za.co.jacon.btc.aggregator.accumulator.Accumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.exchange.bitfinex.accumulator.BitfinexAccumulator;
import za.co.jacon.btc.aggregator.exchange.bitfinex.api.BitfinexApi;
import za.co.jacon.btc.aggregator.exchange.bitstamp.accumulator.BitstampAccumulator;
import za.co.jacon.btc.aggregator.exchange.bitstamp.api.BitstampApi;
import za.co.jacon.btc.aggregator.exchange.bitx.accumulator.BitXAccumulator;
import za.co.jacon.btc.aggregator.exchange.bitx.api.BitXApi;
import za.co.jacon.btc.aggregator.exchange.btce.accumulator.BtceAccumulator;
import za.co.jacon.btc.aggregator.exchange.btce.api.BtceApi;
import za.co.jacon.btc.aggregator.exchange.cryptsy.accumulator.CryptsyAccumulator;
import za.co.jacon.btc.aggregator.exchange.okcoin.accumulator.OKCoinAccumulator;
import za.co.jacon.btc.aggregator.exchange.okcoin.api.OKCoinApi;

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

    /**
     * Configures and set's up the btce accumulator.
     *
     * @param environment the spring environment
     * @param api the configure btce api
     * @param distributor the configured distributor
     *
     * @return the configured btce accumulator.
     */
    @Bean
    public Accumulator btceAccumulator(final Environment environment, final BtceApi api, final Distributor distributor) {
        final int pollDelay = environment.getRequiredProperty("exchange.btce.accumulator.poll_delay_in_seconds", Integer.class);
        return new BtceAccumulator(distributor, api, pollDelay);
    }

    /**
     * Configures and set's up the bitx accumulator.
     *
     * @param environment the spring environment
     * @param api the configure bitx api
     * @param distributor the configured distributor
     *
     * @return the configured bitx accumulator.
     */
    @Bean
    public Accumulator bitXAccumulator(final Environment environment, final BitXApi api, final Distributor distributor) {
        final int pollDelay = environment.getRequiredProperty("exchange.bitx.accumulator.poll_delay_in_seconds", Integer.class);
        return new BitXAccumulator(distributor, api, pollDelay);
    }

    /**
     * Configures and set's up the okcoin accumulator.
     *
     * @param environment the spring environment
     * @param api the configure okcoin api
     * @param distributor the configured distributor
     *
     * @return the configured okcoin accumulator.
     */
    @Bean
    public Accumulator okcoinAccumulator(final Environment environment, final OKCoinApi api, final Distributor distributor) {
        final int pollDelay = environment.getRequiredProperty("exchange.okcoin.accumulator.poll_delay_in_seconds", Integer.class);
        return new OKCoinAccumulator(distributor, api, pollDelay);
    }


    /**
     * Configures the cryptsy accumulator as a spring bean in the DiC.
     *
     * @param environment the spring environment
     * @param mapper the configured object mapper
     * @param distributor the configured distributor
     *
     * @return the configure cryptsy accumulator
     */
    @Bean
    public Accumulator cryptsyAccumulator(final Environment environment, final ObjectMapper mapper, final Distributor distributor) {
        final String pusherKey = environment.getRequiredProperty("exchange.crypsty.accumulator.pusher_key", String.class);
        final String pusherChannel = environment.getRequiredProperty("exchange.crypsty.accumulator.pusher_channel", String.class);
        final String pusherEvent = environment.getRequiredProperty("exchange.crypsty.accumulator.pusher_event", String.class);

        return new CryptsyAccumulator(distributor, new Pusher(pusherKey), pusherChannel, pusherEvent, mapper);
    }
}
