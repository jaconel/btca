package za.co.jacon.btc.aggregator.exchange.cryptsy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.accumulator.PusherAccumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;

import java.io.IOException;

/**
 * The cryptsy accumulator.
 *
 * Responsible for accumulating cryptsy exchange data and distributing it to the various distributors.
 */
public class CryptsyAccumulator extends PusherAccumulator {

    Logger LOGGER = Logger.getLogger(CryptsyAccumulator.class);

    private final ObjectMapper mapper;

    /**
     * Instantiate the cryptsy accumulator with the configured Pusher api.
     *
     * @param distributor the message distributor
     * @param pusherApi the configured pusher api
     * @param channel the pusher channel to connect to
     * @param event the push event to bind to
     * @param mapper the object mapper for message serialization
     */
    public CryptsyAccumulator(final Distributor distributor, final Pusher pusherApi, final String channel, final String event, ObjectMapper mapper) {
        super(distributor, pusherApi, channel, event);

        this.mapper = mapper;
    }

    @Override
    public void onEvent(String channelName, String eventName, String data) {
        try {
            JsonNode rootNode = mapper.readValue(data, JsonNode.class);
            JsonNode jsonNode = rootNode.get("trade");

            Ticker ticker = mapper.readValue(jsonNode.toString(), Ticker.class);

            this.distributor.distribute(ticker, "cryptsy");

        } catch (IOException e) {
            LOGGER.error("Unable to convert ticker json data to Ticker POJO. " + e.getMessage());
        }
    }
}
