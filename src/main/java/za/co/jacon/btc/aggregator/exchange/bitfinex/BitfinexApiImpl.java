package za.co.jacon.btc.aggregator.exchange.bitfinex;

import org.apache.log4j.Logger;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestOperations;

/**
 * Implementation of the bitfinex api.
 */
public class BitfinexApiImpl implements BitfinexApi {

    /**
     * Configured instace of the rest operations.
     */
    private final RestOperations rest;

    /**
     * Application logger. Instance of the log4j logger.
     */
    private static final Logger LOGGER = Logger.getLogger(BitfinexApiImpl.class);

    /**
     * The uri to the bitfinex api.
     */
    private static final String API_URL = "https://api.bitfinex.com/v1";

    /**
     * Class constructor.
     *
     * @param rest the rest operations instance
     */
    public BitfinexApiImpl(RestOperations rest) {
        this.rest = rest;
    }

    @Override
    public Ticker ticker() {
        LOGGER.info("Calling Bitfinex api to retrieve ticker data");

        Ticker ticker = null;
        try {
            ticker = this.rest.getForObject(API_URL + "/pubticker/btcusd", Ticker.class);
        } catch (HttpMessageNotReadableException ex) {
            LOGGER.error("Unable to deserialize the bitx ticker api response. It might be that the api returned and error. Original exception was: " + ex.getMessage());
        }
        return ticker;
    }
}
