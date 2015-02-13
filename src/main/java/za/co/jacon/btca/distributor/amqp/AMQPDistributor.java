package za.co.jacon.btca.distributor.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import za.co.jacon.btca.distributor.Distributor;

/**
 * Distributes accumulated data via AMQP.
 */
public abstract class AMQPDistributor<T> implements Distributor<T> {

    /**
     * The amqp template for pushing messages on the queue.
     */
    protected final AmqpTemplate amqp;

    /**
     * Class constuctor.
     *
     * @param amqp injected, configured, instance of the amqp template for communication with rabbitmq
     */
    public AMQPDistributor(final AmqpTemplate amqp) {
        this.amqp = amqp;
    }
}
