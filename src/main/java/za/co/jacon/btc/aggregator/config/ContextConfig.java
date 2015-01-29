package za.co.jacon.btc.aggregator.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import za.co.jacon.btc.aggregator.accumulator.Accumulator;
import za.co.jacon.btc.aggregator.accumulator.AccumulatorCoordinator;
import za.co.jacon.btc.aggregator.distributor.AMQPDistributor;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.rest.ResponseErrorHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Context configuration for setting up spring dependency injection.
 */
@Configuration
@Import({AMQPConfig.class, ExchangeApiConfig.class, AccumulatorConfig.class})
public class ContextConfig {

    @Bean
    public RestOperations restTemplate(final MappingJackson2HttpMessageConverter jackson2HttpMessageConverter, final ResponseErrorHandler errorHandler) {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(jackson2HttpMessageConverter);

        RestTemplate rest = new RestTemplate();
        rest.setMessageConverters(messageConverters);
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
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.CLOSE_CLOSEABLE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter(final ObjectMapper mapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(mapper);

        return converter;
    }
}
