package za.co.jacon.btc.aggregator.exchange.bitstamp.accumulator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.accumulator.PusherAccumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.exchange.bitstamp.model.TickerVO;

import java.io.IOException;

/**
 * The bitstamp accumulator.
 *
 * Responsible for accumulating bitstamp exchange data and distributing it to the various distributors.
 */
public class BitstampPusherAccumulator extends PusherAccumulator {

    Logger LOGGER = Logger.getLogger(BitstampPusherAccumulator.class);

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
    public BitstampPusherAccumulator(final Distributor distributor, final Pusher pusherApi, final String channel, final String event, final ObjectMapper mapper) {
        super(distributor, pusherApi, channel, event);

        LOGGER.debug("Initiating " + BitstampPusherAccumulator.class);

        this.mapper = mapper;
    }

    @Override
    public void onEvent(String channelName, String eventName, String data) {
        LOGGER.info("Bitstamp Event received from " + channelName + ", event name was " + eventName + ": " + data);

        try {
            TickerVO tickerVO = mapper.readValue(data, TickerVO.class);

            this.distributor.distribute(tickerVO, "bitstamp");
        } catch (IOException e) {
            LOGGER.error("Unable to convert ticker json data to Ticker POJO. " + e.getMessage());
        }
    }

}
