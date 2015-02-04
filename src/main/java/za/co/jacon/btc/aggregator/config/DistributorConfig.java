package za.co.jacon.btc.aggregator.config;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import za.co.jacon.btc.aggregator.distributor.amqp.AMQPDistributor;
import za.co.jacon.btc.aggregator.distributor.Distributor;
import za.co.jacon.btc.aggregator.distributor.amqp.AMQPPriceDistributor;
import za.co.jacon.btc.aggregator.distributor.mongo.MongoPriceDistributor;
import za.co.jacon.btc.aggregator.distributor.statsd.StatsdDistributor;
import za.co.jacon.btc.aggregator.distributor.statsd.StatsdPriceDistributor;
import za.co.jacon.btc.aggregator.model.TransactionVO;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Configuration related to the setup of any/all distributors.
 */
@Configuration
public class DistributorConfig {

    /**
     * Configure a distributor which will distribute data via a amqp message queue.
     * @param amqpTemplate the amqp template for interacting with the message queue.
     * @return the configured distributor
     */
    @Bean
    public Distributor<TransactionVO> AMQPDistributor(final AmqpTemplate amqpTemplate) {
        return new AMQPPriceDistributor(amqpTemplate);
    }

    /**
     * Configure a distributor which will make use of statsd to distribute metric related to the data.
     * @param statsDClient the configures statsd client.
     * @return the configured distributor.
     */
    @Bean
    public Distributor<TransactionVO> statsdPriceDistributor(final StatsDClient statsDClient) {
        return new StatsdPriceDistributor(statsDClient);
    }

    /**
     * Configure distributor to store price info in mongodb.
     * @param mongoClient the configured mongodb client
     * @param environment the spring environment.
     * @return the configured distributor
     */
    @Bean
    public Distributor<TransactionVO> mongodbPriceDistributor(final MongoClient mongoClient, final Environment environment) {
        final String dbName = environment.getRequiredProperty("mongodb.db_name");
        final DB mongodb = mongoClient.getDB(dbName);

        return new MongoPriceDistributor(mongodb);
    }

    /**
     * Returns a non-blocking statsd client.
     * @return the statsd client
     */
    @Bean
    public StatsDClient statsDClient(Environment environment) {
        final String statsdPrefix = environment.getRequiredProperty("statsd.prefix");
        final String statsdHost = environment.getRequiredProperty("statsd.host");
        final Integer statsdPort = environment.getRequiredProperty("statsd.port", Integer.class);

        return new NonBlockingStatsDClient(statsdPrefix, statsdHost, statsdPort);
    }

    /**
     * Configures the mongodb client in the DiC.
     *
     * @param environment the spring environment
     *
     * @return the configured mongodb client.
     *
     * @throws UnknownHostException when unable to connect to the host
     */
    @Bean
    public MongoClient mongoClient(final Environment environment) throws UnknownHostException {

        String host = environment.getRequiredProperty("mongodb.host");
        Integer port = environment.getRequiredProperty("mongodb.port", Integer.class);

        MongoClient mongoClient = new MongoClient(
            Arrays.asList(
                new ServerAddress(host, port)
            )
        );
        mongoClient.setWriteConcern(WriteConcern.SAFE);

        return mongoClient;
    }
}
