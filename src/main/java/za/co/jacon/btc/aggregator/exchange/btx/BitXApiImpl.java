package za.co.jacon.btc.aggregator.exchange.btx;


import org.apache.log4j.Logger;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestOperations;

/**
 * An implementation of the BitXApi responsible for integration with the BitX exchange.
 */
public class BitXApiImpl implements BitXApi {

    private final Logger LOGGER = Logger.getLogger(BitXApiImpl.class);
    private final RestOperations rest;

    private static final String API_URL = "https://api.mybitx.com/api";

    public BitXApiImpl(RestOperations rest) {
        this.rest = rest;
    }

    @Override
    public Ticker ticker() {
        LOGGER.info("Calling BitX api to retrieve ticker data");

        Ticker ticker = null;
        try {
            ticker = this.rest.getForObject(API_URL + "/1/ticker?pair=XBTUSD", Ticker.class);
        } catch (HttpMessageNotReadableException ex) {
            LOGGER.error("Unable to deserialize the bitx ticker api response. It might be that the api returned and error. Original exception was: " + ex.getMessage());
        }
        return ticker;
    }

}
