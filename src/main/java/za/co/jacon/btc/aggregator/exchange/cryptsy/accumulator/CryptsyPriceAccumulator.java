package za.co.jacon.btc.aggregator.exchange.cryptsy.accumulator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pusher.client.Pusher;
import org.apache.log4j.Logger;
import za.co.jacon.btc.aggregator.accumulator.PusherAccumulator;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.exchange.cryptsy.model.TransactionVO;

import java.io.IOException;
import java.util.List;

/**
 * The cryptsy accumulator.
 *
 * Responsible for accumulating cryptsy exchange data and distributing it to the various distributors.
 */
public class CryptsyPriceAccumulator extends PusherAccumulator {

    /**
     * An instance of the application logger.
     */
    Logger LOGGER = Logger.getLogger(CryptsyPriceAccumulator.class);

    /**
     * An instance of the configured object mapper.
     */
    private final ObjectMapper mapper;

    /**
     * A reference name for the current exchange.
     */
    public static final String EXCHANGE_REFERENCE = "cryptsy";

    /**
     * Instantiate the cryptsy accumulator with the configured Pusher api.
     *
     * @param distributors the message distributors
     * @param pusherApi the configured pusher api
     * @param channel the pusher channel to connect to
     * @param event the push event to bind to
     * @param mapper the object mapper for message serialization
     */
    public CryptsyPriceAccumulator(final List<Distributor> distributors, final Pusher pusherApi, final String channel, final String event, final ObjectMapper mapper) {
        super(distributors, pusherApi, channel, event);

        this.mapper = mapper;
    }

    @Override
    public void onEvent(String channelName, String eventName, String data) {
        try {
            JsonNode rootNode = mapper.readValue(data, JsonNode.class);
            JsonNode jsonNode = rootNode.get("trade");

            TransactionVO transaction = mapper.readValue(jsonNode.toString(), TransactionVO.class);

            for (Distributor distributor: distributors) {
                distributor.distribute(transaction, EXCHANGE_REFERENCE);
            }

        } catch (IOException e) {
            LOGGER.error("Unable to convert ticker json data to Ticker POJO. " + e.getMessage());
        }
    }
}
