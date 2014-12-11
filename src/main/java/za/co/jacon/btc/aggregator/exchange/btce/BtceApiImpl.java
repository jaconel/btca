package za.co.jacon.btc.aggregator.exchange.btce;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;

import java.io.IOException;

/**
 * Implementation of the BTC-e api.
 */
public class BtceApiImpl implements BtceApi {

    private static final Logger LOGGER = Logger.getLogger(BtceApiImpl.class);
    private static final String API_URL = "https://btc-e.com/api/3";

    /**
     * Instance of the configured restoperations.
     */
    private final RestOperations rest;
    private final ObjectMapper mapper;

    /**
     * Class constructor.
     *
     * Allows for injecting the restoperations instance..
     *
     * @param rest restoperations instance.
     */
    public BtceApiImpl(final RestOperations rest, ObjectMapper mapper) {
        this.rest = rest;
        this.mapper = mapper;
    }

    @Override
    public Ticker ticker() {
        ResponseEntity<String> response = this.rest.getForEntity(API_URL + "/ticker/btc_usd", String.class);

        Ticker ticker = null;
        try {
            JsonNode rootNode = mapper.readValue(response.getBody(), JsonNode.class);
            JsonNode jsonNode = rootNode.get("btc_usd");

            ticker = mapper.readValue(jsonNode.toString(), Ticker.class);
        } catch (IOException e) {
            LOGGER.error("Unable to convert ticker json data to Ticker POJO. " + e.getMessage());
        }

        return ticker;
    }
}
