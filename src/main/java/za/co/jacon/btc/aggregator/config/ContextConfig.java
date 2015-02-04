package za.co.jacon.btc.aggregator.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import za.co.jacon.btc.aggregator.accumulator.Accumulator;
import za.co.jacon.btc.aggregator.accumulator.AccumulatorCoordinator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.rest.ResponseErrorHandler;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;

/**
 * Context configuration for setting up spring dependency injection.
 */
@Configuration
@Import({AMQPConfig.class, ExchangeApiConfig.class, AccumulatorConfig.class, DistributorConfig.class})
public class ContextConfig {

    @Bean
    public RestOperations restTemplate(final ObjectMapper mapper, final ResponseErrorHandler errorHandler) {
        // setup all the message converters to be used with the rest template
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter<Source>());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter(mapper));

        // create the rest template
        RestTemplate rest = new RestTemplate(messageConverters);
        rest.setErrorHandler(errorHandler);

        return rest;
    }

    @Bean
    public ResponseErrorHandler responseErrorHandler() {
        return new ResponseErrorHandler();
    }


    @Bean
    public AccumulatorCoordinator getCoordinator(final List<Accumulator> accumulators) {
        return new AccumulatorCoordinator(accumulators);
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.CLOSE_CLOSEABLE);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return mapper;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter(final ObjectMapper mapper) {
        return new MappingJackson2HttpMessageConverter(mapper);
    }
}
