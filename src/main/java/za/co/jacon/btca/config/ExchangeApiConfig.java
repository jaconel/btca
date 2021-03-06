package za.co.jacon.btca.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import za.co.jacon.btca.exchange.bitfinex.api.BitfinexApi;
import za.co.jacon.btca.exchange.bitfinex.api.BitfinexApiImpl;
import za.co.jacon.btca.exchange.bitstamp.api.BitstampApi;
import za.co.jacon.btca.exchange.bitstamp.api.BitstampApiImpl;
import za.co.jacon.btca.exchange.btce.api.BtceApi;
import za.co.jacon.btca.exchange.btce.api.BtceApiImpl;
import za.co.jacon.btca.exchange.bitx.api.BitXApi;
import za.co.jacon.btca.exchange.bitx.api.BitXApiImpl;
import za.co.jacon.btca.exchange.okcoin.api.OKCoinApi;
import za.co.jacon.btca.exchange.okcoin.api.OKCoinApiImpl;

/**
 * Configures the exchange api's in the spring dependency injection container.
 */
@Configuration
public class ExchangeApiConfig {

    /**
     * Set's up the bitXApi as a spring bean in the DiC
     *
     * @param restOperations the configure restOperations instance.
     *
     * @return the configured bitXApi
     */
    @Bean
    public BitXApi bitXApi(final RestOperations restOperations, final ObjectMapper objectMapper) {
        return new BitXApiImpl(restOperations, objectMapper);
    }

    /**
     * Sets up the bitfinexapi as a spring bean in the DiC.
     *
     * @param restOperations the configured rest operations instance.
     * @param objectMapper the configured object mapper.
     *
     * @return the configured bitfinex api
     */
    @Bean
    public BitfinexApi bitfinexApi(final RestOperations restOperations, final ObjectMapper objectMapper) {
        return new BitfinexApiImpl(restOperations, objectMapper);
    }

    /**
     * Configures the btc api as a spring bean in the DiC.
     *
     * @param restOperations the configured rest operations instance
     * @param objectMapper the configured object mapper.
     *
     * @return the configure btce api
     */
    @Bean
    public BtceApi btceApi(final RestOperations restOperations, final ObjectMapper objectMapper) {
        return new BtceApiImpl(restOperations, objectMapper);
    }

    /**
     * Configures the bitstamp api as a spring bean in the DiC.
     *
     * @param restOperations the configured rest operations instance.
     *
     * @return the configured bitstamp api
     */
    @Bean
    public BitstampApi bitstampApi(final RestOperations restOperations) {
        return new BitstampApiImpl(restOperations);
    }

    /**
     * Configures the okcoin api as a spring bean in the DiC.
     *
     * @param restOperations the configured rest operations instance
     * @param mapper the configured object mapper.
     *
     * @return the configure okcoin api
     */
    @Bean
    public OKCoinApi okCoinApi(final RestOperations restOperations, final ObjectMapper mapper) {
        return new OKCoinApiImpl(restOperations, mapper);

    }
}
