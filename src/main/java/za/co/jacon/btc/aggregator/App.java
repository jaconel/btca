package za.co.jacon.btc.aggregator;

import com.pusher.client.Pusher;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.ChannelEventListener;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.Log4jConfigurer;
import za.co.jacon.btc.aggregator.config.ContextConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App 
{

    public static final Logger LOGGER = Logger.getLogger(App.class);

    public static void main( String[] args )
    {
        new App().run();
    }

    public void run() {
        this.setupLogger();
        this.setupApplicationContext();

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            LOGGER.error("Thread sleep was interrupted.");
            System.exit(1);
        }
    }

    private void setupLogger() {
        try {
            Log4jConfigurer.initLogging("classpath:Logging.properties", 5000L);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to initialize the logger due to missing Logging.properties file. System will halt.");
            System.exit(1);
        }
        LOGGER.info("Application logging configured.");
    }

    private void setupApplicationContext() {
        LOGGER.info("Setting up application context.");

        ConfigurableEnvironment environment = setupApplicationEnvironment();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.setEnvironment(environment);
        context.register(ContextConfig.class);
        context.refresh();
        context.registerShutdownHook();

        LOGGER.info("Application context has been set up.");
    }

    private ConfigurableEnvironment setupApplicationEnvironment() {
        Properties properties = null;
        ConfigurableEnvironment environment = null;
        try {
            properties = loadPropertiesFromPath();
        } catch (IOException e) {
            LOGGER.error("Unable to close the properties input stream. IO Exception thrown with message: " + e.getMessage());
        }

        if (properties != null) {
            LOGGER.info("Injecting property source in to the standard application environment.");
            PropertiesPropertySource propertySource = new PropertiesPropertySource("classpath::ApplicationProperties", properties);

            environment = new StandardEnvironment();
            environment.getPropertySources().addFirst(propertySource);
            LOGGER.info("Property source successfully injected into standard application environment.");
        }

        return environment;
    }

    /**
     * Loads the property file form the classpath and populates a properties object.
     * This properties object then gets returned.
     *
     * @return populated properties object
     * @throws IOException if we are unable to close the input stream.
     */
    private Properties loadPropertiesFromPath() throws IOException {
        LOGGER.info("Loading properties from the classpath input stream.");

        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = App.class.getClassLoader().getResourceAsStream("Application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("An IO exception occurred while attempting to load the properties from class path input stream. Error message: " + e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }

        LOGGER.info("Properties successfully loaded from classpath input stream.");

        return properties;
    }
}
