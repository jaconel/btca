package za.co.jacon.btca.config;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import za.co.jacon.btca.distributor.Distributor;
import za.co.jacon.btca.distributor.amqp.AMQPPriceDistributor;
import za.co.jacon.btca.distributor.mongo.MongoPriceDistributor;
import za.co.jacon.btca.distributor.statsd.StatsdPriceDistributor;
import za.co.jacon.btca.model.TransactionVO;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Configuration related to the setup of any/all distributors.
 */
@Configuration
public class DistributorConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistributorConfig.class);

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
        final String dbName = environment.getRequiredProperty("btca.mongodb.db_name");

        LOGGER.info("Setting mongo database name to %s", dbName);

        final DB mongodb = mongoClient.getDB(dbName);

        return new MongoPriceDistributor(mongodb);
    }

    /**
     * Returns a non-blocking statsd client.
     * @return the statsd client
     */
    @Bean
    public StatsDClient statsDClient(Environment environment) {
        final String statsdPrefix = environment.getRequiredProperty("btca.statsd.prefix");
        final String statsdHost = environment.getRequiredProperty("btca.statsd.host");
        final Integer statsdPort = environment.getRequiredProperty("btca.statsd.port", Integer.class);

        LOGGER.info(
            String.format(
                "Creating NonBlockingStatsDClient with the following parameters: prefix=%s | host=%s | port=%s",
                statsdPrefix, statsdHost, statsdPort
            )
        );

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

        String host = environment.getRequiredProperty("btca.mongodb.host");
        Integer port = environment.getRequiredProperty("btca.mongodb.port", Integer.class);

        LOGGER.info(
            String.format(
                "Creating MongoClient with the following parameters: host=%s | port=%s",
                host, port
            )
        );

        MongoClient mongoClient = new MongoClient(
            Arrays.asList(
                new ServerAddress(host, port)
            )
        );
        mongoClient.setWriteConcern(WriteConcern.SAFE);

        return mongoClient;
    }
}
