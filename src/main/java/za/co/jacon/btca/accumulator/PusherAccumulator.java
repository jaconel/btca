package za.co.jacon.btca.accumulator;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import org.apache.log4j.Logger;
import za.co.jacon.btca.distributor.Distributor;

import java.util.List;

/**
 * Accumulator that makes use of the pusher api for receiving ticker data over a websocket.
 *
 * All objects extending the PusherAccumulator is expected to implement the onEvent().
 */
public abstract class PusherAccumulator<T> extends AbstractAccumulator<T> implements  ConnectionEventListener, SubscriptionEventListener {

    protected final Pusher pusher;
    protected final String channel;
    protected final String event;

    private static final Logger LOGGER = Logger.getLogger(PusherAccumulator.class);


    /**
     * Class constructor.
     *
     * Initializes the pusher api etc.
     *
     * @param distributors the message distributor
     * @param pusherApi the configured pusher api
     * @param channel the channel to which to subscribe
     * @param event the event to which to bind
     */
    public PusherAccumulator(final List<Distributor<T>> distributors, final Pusher pusherApi, final String channel, final String event) {
        super(distributors);

        this.pusher = pusherApi;
        this.channel = channel;
        this.event = event;

    }

    /**
     * Starts the accumulator by connecting to the pusher api, subscribing to the given channel and possibly binding
     * to a specific event.
     */
    @Override
    public void start() {
        this.pusher.connect(this, ConnectionState.ALL);
        Channel channel = this.pusher.subscribe(this.channel);
        if (this.event != null) {
            channel.bind(this.event, this);
        }
    }

    /**
     * Stops the accumulator by elegantly shutting down the pusher connections.
     */
    @Override
    public void stop() {
        this.pusher.disconnect();
    }

    /**
     * Event received when the pusher connection's state changes.
     * @param connectionStateChange the new connection state
     */
    @Override
    public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
        LOGGER.info("Pusher connection state changed to " + connectionStateChange.getCurrentState().toString());
    }

    /**
     * Event received when the pusher connection issues an error.
     * @param message the error message
     * @param code the error code
     * @param e the exceptions associated with the error.
     */
    @Override
    public void onError(String message, String code, Exception e) {
        LOGGER.info("Error connecting to pusher api. " + message);
    }
}
