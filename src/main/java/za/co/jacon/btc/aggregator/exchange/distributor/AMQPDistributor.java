package za.co.jacon.btc.aggregator.exchange.distributor;

/**
 * Distributes accumulated data via AMQP.
 */
public class AMQPDistributor implements Distributor {

    /**
     * This method is responsible for distributing the dataObject for each exchange.
     *
     * Data is distributed onto a rabbitmq exchange.
     *
     * vhost: btca (for btc_aggregator)
     * exhange: raw (for raw_data)
     *
     * the actual exchange name will be used as routing key.
     *
     * @param dataObject
     */
    public void distribute(Object dataObject, String exchange) {



    }
}
