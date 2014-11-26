package za.co.jacon.btc.aggregator.exchange.cryptsy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.exchange.accumulator.Accumulator;
import za.co.jacon.btc.aggregator.exchange.distributor.Distributor;

import java.io.IOException;

/**
 * The cryptsy accumulator.
 *
 * Responsible for accumulating cryptsy exchange data and distributing it to the various distributors.
 */
public class CryptsyAccumulator implements Accumulator, ConnectionEventListener, SubscriptionEventListener {

    Logger LOGGER = Logger.getLogger(CryptsyAccumulator.class);

    private final Pusher pusher;
    private final String channel;
    private final ObjectMapper mapper;

    /**
     * Instantiate the cryptsy accumulator with the configured Pusher api.
     *
     * @param pusherApi the configured pusher api
     */
    public CryptsyAccumulator(final Pusher pusherApi, final String channel, ObjectMapper mapper) {
        this.pusher = pusherApi;
        this.channel = channel;
        this.mapper = mapper;
    }

    @Override
    public void start(Distributor distributor) {
        LOGGER.info("Initiating connection to cryptsy accumulator");

        this.pusher.connect(this, ConnectionState.ALL);
        Channel channel = this.pusher.subscribe(this.channel);
        channel.bind("message", this);
    }

    @Override
    public void stop() {
        this.pusher.disconnect();
    }

    @Override
    public void onEvent(String channelName, String eventName, String data) {
        LOGGER.info("Event received from " + channelName + ", event name was " + eventName + ": " + data);

        try {
            JsonNode rootNode = mapper.readValue(data, JsonNode.class);
            JsonNode jsonNode = rootNode.get("trade");

            Ticker ticker = mapper.readValue(jsonNode.toString(), Ticker.class);

            LOGGER.info(ticker.getTopSell().getPrice());

        } catch (IOException e) {
            LOGGER.error("Unable to convert ticker json data to Ticker POJO. " + e.getMessage());
        }
    }

    @Override
    public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
        LOGGER.info("Pusher connection state changed to " + connectionStateChange.getCurrentState().toString());
    }

    @Override
    public void onError(String message, String code, Exception e) {
        LOGGER.info("Error connecting to pusher api. " + message);
    }

}
