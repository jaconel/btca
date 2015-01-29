package za.co.jacon.btc.aggregator.exchange.bitstamp.api;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import za.co.jacon.btc.aggregator.exchange.bitstamp.model.TransactionVO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of the bitstamp api.
 */
public class BitstampApiImpl implements BitstampApi {

    private static final Logger LOGGER = Logger.getLogger(BitstampApiImpl.class);
    private static final String API_URL = "https://www.bitstamp.net/api/";

    private final RestOperations rest;

    /**
     * The class constructor.
     * @param restOperations the injected rest template (http client).
     */
    public BitstampApiImpl(RestOperations restOperations) {
        this.rest = restOperations;
    }

    /**
     * Retrieves a list of the latest transactions from the exchange.
     *
     * @return a list of transaction objects
     */
    @Override
    public List<TransactionVO> getListOfLatestTransactions() {
        ResponseEntity<TransactionVO[]> response = this.rest.getForEntity(API_URL + "/transactions/?time=minute", TransactionVO[].class);
        if (response.getStatusCode() != HttpStatus.OK) {
            LOGGER.error("Non 200 OK status code received for retrieval of trades from bitfinex.");
        }
        List<TransactionVO> transactions = Arrays.asList(response.getBody());
        Collections.sort(transactions, (tx1, tx2) -> (tx1.getDate() > tx2.getDate()) ? 1 : 0);

        return transactions;
    }

    /**
     * Retrieves the latest transaction from the exchange.
     *
     * @return the latest transaction object
     */
    @Override
    public TransactionVO getLatestTransaction() {
        List<TransactionVO> transactions = getListOfLatestTransactions();
        if (!transactions.isEmpty()) {
            return transactions.get(0);
        }
        return null;
    }
}
