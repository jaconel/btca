package za.co.jacon.btc.aggregator.rest;

import org.apache.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * Response error handler for the rest operations implementation;
 */
public class ResponseErrorHandler implements org.springframework.web.client.ResponseErrorHandler {

    private org.springframework.web.client.ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    private static final Logger LOGGER = Logger.getLogger(ResponseErrorHandler.class);

    public boolean hasError(ClientHttpResponse response) throws IOException {
        return errorHandler.hasError(response);
    }

    public void handleError(ClientHttpResponse response) throws IOException {
        LOGGER.error("An error occurred during rest operations. Status code returned was " + response.getRawStatusCode() + "(" + response.getStatusText() + ").");
    }
}
