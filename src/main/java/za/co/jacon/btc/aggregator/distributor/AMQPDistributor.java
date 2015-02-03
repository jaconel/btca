package za.co.jacon.btc.aggregator.distributor;

import org.springframework.amqp.core.AmqpTemplate;

/**
 * Distributes accumulated data via AMQP.
 */
public class AMQPDistributor implements Distributor {

    /**
     * The amqp template for pushing messages on the queue.
     */
    private final AmqpTemplate amqp;

    /**
     * Class constuctor.
     *
     * @param amqp injected, configured, instance of the amqp template for communication with rabbitmq
     */
    public AMQPDistributor(final AmqpTemplate amqp) {
        this.amqp = amqp;
    }

    /**
     * This method is responsible for distributing the dataObject for each exchange.
     *
     * Data is distributed onto a rabbitmq exchange.
     *
     * The exchange name will be used as routing key.
     *
     * @param dataObject the object to store in the queue
     */
    public void distribute(Object dataObject, String exchange) {
        this.amqp.convertAndSend(exchange, dataObject);
    }
}
