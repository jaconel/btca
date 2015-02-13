package za.co.jacon.btca;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import za.co.jacon.btca.config.ContextConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Hello world!
 *
 */
public class BtcaIntializer
{

    public static final Logger LOGGER = LoggerFactory.getLogger(BtcaIntializer.class);


    public static void main( String[] args )
    {
        new BtcaIntializer().run();
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
        InputStream loggingProperties = BtcaIntializer.class.getClassLoader().getResourceAsStream("Logging.properties");
        if (loggingProperties == null) {
            System.out.println("Unable to locate the Logging.properties file on the classpath. System halting.");
            System.exit(0);
        }
        PropertyConfigurator.configure(loggingProperties);
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
            inputStream = BtcaIntializer.class.getClassLoader().getResourceAsStream("Application.properties");
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
