package za.co.jacon.btc.aggregator.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import za.co.jacon.btc.aggregator.exchange.accumulator.Accumulator;
import za.co.jacon.btc.aggregator.exchange.accumulator.AccumulatorCoordinator;
import za.co.jacon.btc.aggregator.exchange.bitfinex.BitfinexAccumulator;
import za.co.jacon.btc.aggregator.exchange.bitfinex.BitfinexApi;
import za.co.jacon.btc.aggregator.exchange.bitfinex.BitfinexApiImpl;
import za.co.jacon.btc.aggregator.exchange.bitstamp.BitstampAccumulator;
import za.co.jacon.btc.aggregator.exchange.btce.BtceAccumulator;
import za.co.jacon.btc.aggregator.exchange.btce.BtceApi;
import za.co.jacon.btc.aggregator.exchange.btce.BtceApiImpl;
import za.co.jacon.btc.aggregator.exchange.btx.BitXAccumulator;
import za.co.jacon.btc.aggregator.exchange.btx.BitXApi;
import za.co.jacon.btc.aggregator.exchange.btx.BitXApiImpl;
import za.co.jacon.btc.aggregator.exchange.cryptsy.CryptsyAccumulator;
import za.co.jacon.btc.aggregator.distributor.AMQPDistributor;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.rest.ResponseErrorHandler;

import java.util.List;

/**
 * Context configuration for setting up spring dependency injection.
 */
@Configuration
@Import(AMQPConfig.class)
public class ContextConfig {


    @Bean
    public Accumulator cryptsyAccumulator(final Environment environment, final ObjectMapper mapper, final Distributor distributor) {
        final String pusherKey = environment.getRequiredProperty("exchange.crypsty.accumulator.pusher_key", String.class);
        final String pusherChannel = environment.getRequiredProperty("exchange.crypsty.accumulator.pusher_channel", String.class);
        final String pusherEvent = environment.getRequiredProperty("exchange.crypsty.accumulator.pusher_event", String.class);

        return new CryptsyAccumulator(distributor, new Pusher(pusherKey), pusherChannel, pusherEvent, mapper);
    }

    @Bean
    public Accumulator bitstampAccumulator(final Environment environment, final ObjectMapper mapper, final Distributor distributor) {
        final String pusherKey = environment.getRequiredProperty("exchange.bitstamp.accumulator.pusher_key", String.class);
        final String pusherChannel = environment.getRequiredProperty("exchange.bitstamp.accumulator.pusher_channel", String.class);
        final String pusherEvent = environment.getRequiredProperty("exchange.bitstamp.accumulator.pusher_event", String.class);

        return new BitstampAccumulator(distributor, new Pusher(pusherKey), pusherChannel, pusherEvent, mapper);
    }

    @Bean
    public Accumulator bitXAccumulator(final Environment environment, final BitXApi bitXApi, final Distributor distributor) {
        final int pollDelay = environment.getRequiredProperty("exchange.bitx.accumulator.poll_delay_in_seconds", Integer.class);
        return new BitXAccumulator(distributor, bitXApi, pollDelay);
    }

    @Bean
    public BitXApi bitXApi(final RestOperations restOperations) {
        return new BitXApiImpl(restOperations);
    }

    @Bean
    public Accumulator bitfinexAccumulator(final Environment environment, final BitfinexApi bitfinexApi, final Distributor distributor) {
        final int pollDelay = environment.getRequiredProperty("exchange.bitfinex.accumulator.poll_delay_in_seconds", Integer.class);
        return new BitfinexAccumulator(distributor, bitfinexApi, pollDelay);
    }

    @Bean
    public BitfinexApi bitfinexApi(final RestOperations restOperations) {
        return new BitfinexApiImpl(restOperations);
    }

    @Bean
    public Accumulator btceAccumulator(final Environment environment, final BtceApi api, final Distributor distributor) {
        final int pollDelay = environment.getRequiredProperty("exchange.btce.accumulator.poll_delay_in_seconds", Integer.class);
        return new BtceAccumulator(distributor, api, pollDelay);
    }

    @Bean
    public BtceApi btceApi(final RestOperations restOperations, final ObjectMapper objectMapper) {
        return new BtceApiImpl(restOperations, objectMapper);
    }

    @Bean
    public RestOperations restTemplate(final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter, final ResponseErrorHandler errorHandler) {
        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(0, jackson2HttpMessageConverter);
        rest.setErrorHandler(errorHandler);

        return rest;
    }

    @Bean
    public ResponseErrorHandler responseErrorHandler() {
        return new ResponseErrorHandler();
    }

    @Bean
    public Distributor getAMQPDistributor(final AmqpTemplate amqpTemplate) {
        return new AMQPDistributor(amqpTemplate);
    }

    @Bean
    public AccumulatorCoordinator getCoordinator(final List<Accumulator> accumulators, final Distributor distributor) {
        return new AccumulatorCoordinator(accumulators, distributor);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter(final ObjectMapper mapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);

        return converter;
    }
}
