package za.co.jacon.btca.distributor.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import za.co.jacon.btca.model.TransactionVO;

/**
 * Distributor responsible for distributing transactional data via amqp.
 */
public class AMQPPriceDistributor extends AMQPDistributor<TransactionVO> {

    /**
     * Class constructor.
     *
     * @param amqpTemplate the amqp client
     */
    public AMQPPriceDistributor(AmqpTemplate amqpTemplate) {
        super(amqpTemplate);
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
    public void distribute(TransactionVO dataObject, String exchange) {
        this.amqp.convertAndSend(exchange, dataObject);
    }

}
