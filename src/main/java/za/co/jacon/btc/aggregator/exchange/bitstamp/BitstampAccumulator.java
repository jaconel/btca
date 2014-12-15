package za.co.jacon.btc.aggregator.exchange.bitstamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.exchange.accumulator.PusherAccumulator;
import za.co.jacon.btc.aggregator.exchange.distributor.Distributor;

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
     * @param distributor the message distributor
     * @param pusherApi the configured pusher api
     * @param channel the pusher channel to connect to
     * @param event the pusher event to bid to
     * @param mapper the object mapper for message serialization
     */
    public BitstampAccumulator(final Distributor distributor, final Pusher pusherApi, final String channel, final String event, final ObjectMapper mapper) {
        super(distributor, pusherApi, channel, event);

        LOGGER.debug("Initiating " + BitstampAccumulator.class);

        this.mapper = mapper;
    }

    @Override
    public void onEvent(String channelName, String eventName, String data) {
        LOGGER.info("Bitstamp Event received from " + channelName + ", event name was " + eventName + ": " + data);

        try {
            Ticker ticker = mapper.readValue(data, Ticker.class);

            this.distributor.distribute(ticker, "bitstamp");
        } catch (IOException e) {
            LOGGER.error("Unable to convert ticker json data to Ticker POJO. " + e.getMessage());
        }
    }

}
