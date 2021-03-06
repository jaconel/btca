package za.co.jacon.btca.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import za.co.jacon.btca.accumulator.Accumulator;
import za.co.jacon.btca.distributor.Distributor;
import za.co.jacon.btca.exchange.bitfinex.accumulator.BitfinexPriceAccumulator;
import za.co.jacon.btca.exchange.bitfinex.api.BitfinexApi;
import za.co.jacon.btca.exchange.bitstamp.accumulator.BitstampPriceAccumulator;
import za.co.jacon.btca.exchange.bitstamp.api.BitstampApi;
import za.co.jacon.btca.exchange.bitx.accumulator.BitXPriceAccumulator;
import za.co.jacon.btca.exchange.bitx.api.BitXApi;
import za.co.jacon.btca.exchange.btce.accumulator.BtcePriceAccumulator;
import za.co.jacon.btca.exchange.btce.api.BtceApi;
import za.co.jacon.btca.exchange.cryptsy.accumulator.CryptsyPriceAccumulator;
import za.co.jacon.btca.exchange.okcoin.accumulator.OKCoinPriceAccumulator;
import za.co.jacon.btca.exchange.okcoin.api.OKCoinApi;
import za.co.jacon.btca.model.TransactionVO;

import java.util.List;

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
     * @param distributors the configured distributors
     *
     * @return the configured bitstamp accumulator.
     */
    @Bean
    public Accumulator bitstampPriceAccumulator(final Environment environment, final BitstampApi bitstampApi, final List<Distributor<TransactionVO>> distributors) {
        final int pollDelay = environment.getRequiredProperty("btca.exchange.bitstamp.accumulator.poll_delay_in_seconds", Integer.class);
        return new BitstampPriceAccumulator(distributors, bitstampApi, pollDelay);
    }

    /**
     * Configures and set's up the bitfinex accumulator.
     *
     * @param environment the spring environment
     * @param bitfinexApi the configure bitstamp api
     * @param distributors the configured distributors
     *
     * @return the configured bitstamp accumulator.
     */
    @Bean
    public Accumulator bitfinexPriceAccumulator(final Environment environment, final BitfinexApi bitfinexApi, final List<Distributor<TransactionVO>> distributors) {
        final int pollDelay = environment.getRequiredProperty("btca.exchange.bitfinex.accumulator.poll_delay_in_seconds", Integer.class);
        return new BitfinexPriceAccumulator(distributors, bitfinexApi, pollDelay);
    }

    /**
     * Configures and set's up the btce accumulator.
     *
     * @param environment the spring environment
     * @param api the configure btce api
     * @param distributors the configured distributors
     *
     * @return the configured btce accumulator.
     */
    @Bean
    public Accumulator btcePriceAccumulator(final Environment environment, final BtceApi api, final List<Distributor<TransactionVO>> distributors) {
        final int pollDelay = environment.getRequiredProperty("btca.exchange.btce.accumulator.poll_delay_in_seconds", Integer.class);
        return new BtcePriceAccumulator(distributors, api, pollDelay);
    }

    /**
     * Configures and set's up the bitx accumulator.
     *
     * @param environment the spring environment
     * @param api the configure bitx api
     * @param distributors the configured distributors
     *
     * @return the configured bitx accumulator.
     */
    @Bean
    public Accumulator bitXPriceAccumulator(final Environment environment, final BitXApi api, final List<Distributor<TransactionVO>> distributors) {
        final int pollDelay = environment.getRequiredProperty("btca.exchange.bitx.accumulator.poll_delay_in_seconds", Integer.class);
        return new BitXPriceAccumulator(distributors, api, pollDelay);
    }

    /**
     * Configures and set's up the okcoin accumulator.
     *
     * @param environment the spring environment
     * @param api the configure okcoin api
     * @param distributors the configured distributors
     *
     * @return the configured okcoin accumulator.
     */
    @Bean
    public Accumulator okcoinPriceAccumulator(final Environment environment, final OKCoinApi api, final List<Distributor<TransactionVO>> distributors) {
        final int pollDelay = environment.getRequiredProperty("btca.exchange.okcoin.accumulator.poll_delay_in_seconds", Integer.class);
        return new OKCoinPriceAccumulator(distributors, api, pollDelay);
    }


    /**
     * Configures the cryptsy accumulator as a spring bean in the DiC.
     *
     * @param environment the spring environment
     * @param mapper the configured object mapper
     * @param distributors the configured distributors
     *
     * @return the configure cryptsy accumulator
     */
    @Bean
    public Accumulator cryptsyPriceAccumulator(final Environment environment, final ObjectMapper mapper, final List<Distributor<TransactionVO>> distributors) {
        final String pusherKey = environment.getRequiredProperty("btca.exchange.crypsty.accumulator.pusher_key", String.class);
        final String pusherChannel = environment.getRequiredProperty("btca.exchange.crypsty.accumulator.pusher_channel", String.class);
        final String pusherEvent = environment.getRequiredProperty("btca.exchange.crypsty.accumulator.pusher_event", String.class);

        return new CryptsyPriceAccumulator(distributors, new Pusher(pusherKey), pusherChannel, pusherEvent, mapper);
    }
}
