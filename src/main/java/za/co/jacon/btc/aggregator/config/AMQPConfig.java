package za.co.jacon.btc.aggregator.config;

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

    @Bean
    public ConnectionFactory connectionFactory(Environment environment) {
        String host = environment.getRequiredProperty("amqp.host");
        String user = environment.getRequiredProperty("amqp.user");
        String pass = environment.getRequiredProperty("amqp.password");
        String vhost = environment.getRequiredProperty("amqp.raw.vhost");

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setUsername(user);
        connectionFactory.setPassword(pass);
        connectionFactory.setVirtualHost(vhost);

        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Environment environment, ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        template.setExchange(environment.getRequiredProperty("amqp.raw.exchange"));

        return template;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange rawDataExchange(Environment environment) {
        return new TopicExchange(environment.getRequiredProperty("amqp.raw.exchange"));
    }

    /**
     * @return the admin bean that can declare queues etc.
     */
    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory, List<Exchange> exchanges) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);

        for (Exchange exchange:exchanges) {
            rabbitAdmin.declareExchange(exchange);
        }

        return rabbitAdmin ;
    }


}
