package za.co.jacon.btca.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

/**
 * Set up the AMQP components in the DiC.
 */
@Configuration
public class AMQPConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(AMQPConfig.class);

    /**
     * Sets up the rabbitmq connection.
     *
     * @param environment the spring environment.
     *
     * @return the connection factory
     */
    @Bean
    public ConnectionFactory connectionFactory(Environment environment) {
        String host = environment.getRequiredProperty("btca.amqp.host");
        String user = environment.getRequiredProperty("btca.amqp.user");
        String pass = environment.getRequiredProperty("btca.amqp.password");
        int port = environment.getProperty("btca.amqp.port", Integer.class, 5672);

        String vhost = environment.getRequiredProperty("btca.amqp.transactions.vhost");


        LOGGER.info(String.format("AQMP credentials are: username=%s | password=%s | host=%s | port=%s | vhost=%s", user, pass, host, port, vhost));

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(user);
        connectionFactory.setPassword(pass);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setPort(port);

        return connectionFactory;
    }

    /**
     * Configures the RabbitTemplate.
     *
     * Sets the message converters and connection factory.
     *
     * @param environment the spring environment
     * @param connectionFactory the connection factory
     * @param messageConverter the message converters
     * @return the configured rabbit template
     */
    @Bean
    public RabbitTemplate rabbitTemplate(Environment environment, ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setExchange(environment.getRequiredProperty("btca.amqp.transactions.exchange"));

        return template;
    }

    /**
     * Setup a default "jackson to json" message converter.
     * @return the message converter.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Creates an AMQPAdmin object.
     *
     * Auto declares the necessary exchange.
     *
     * @param connectionFactory the connection factory
     *
     * @return the amqp admin
     */
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory, List<Exchange> exchanges) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);

        for (Exchange exchange:exchanges) {
            rabbitAdmin.declareExchange(exchange);
        }
        return rabbitAdmin ;
    }

    /**
     * Create the exchange for the btca aggregator.
     *
     * @param environment the spring environment
     *
     * @return the configured exchange
     */
    @Bean
    public Exchange transactionsExchange(final Environment environment) {
        String exchangeName = environment.getRequiredProperty("btca.amqp.transactions.exchange");
        Boolean durable = true;
        Boolean autoDelete = true;

        LOGGER.info(String.format("Creating AMQP exchange %s as durable %s and autoDelete %s", exchangeName, durable, autoDelete));

        return new TopicExchange(exchangeName, durable, autoDelete);
    }

}
