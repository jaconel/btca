package za.co.jacon.btca.rest;

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
        // TODO: raise an IOException containing info describing why the failure occurred.
        LOGGER.error("An error occurred during rest operations. Status code returned was " + response.getRawStatusCode() + "(" + response.getStatusText() + ").");
    }
}
