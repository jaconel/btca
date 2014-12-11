package za.co.jacon.btc.aggregator.exchange.bitstamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.exchange.accumulator.PusherAccumulator;

import java.io.IOException;

/**
 * The bitstamp accumulator.
 *
 * Responsible for accumulating bitstamp exchange data and distributing it to the various distributors.
 */
public class BitstampAccumulator extends PusherAccumulator {

    Logger LOGGER = Logger.getLogger(BitstampAccumulator.class);

    private final ObjectMapper mapper;

    /**
     * Instantiate the cryptsy accumulator with the configured Pusher api.
     *
     * @param pusherApi the configured pusher api
     */
    public BitstampAccumulator(final Pusher pusherApi, final String channel, final String event, final ObjectMapper mapper) {
        super(pusherApi, channel, event);

        this.mapper = mapper;
    }

    @Override
    public void onEvent(String channelName, String eventName, String data) {
        LOGGER.info("Bitstamp Event received from " + channelName + ", event name was " + eventName + ": " + data);

        try {
            Ticker ticker = mapper.readValue(data, Ticker.class);

            LOGGER.info(ticker.getPrice());
        } catch (IOException e) {
            LOGGER.error("Unable to convert ticker json data to Ticker POJO. " + e.getMessage());
        }
    }

}
