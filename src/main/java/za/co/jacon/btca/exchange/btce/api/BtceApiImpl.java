package za.co.jacon.btca.exchange.btce.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import za.co.jacon.btca.exchange.btce.model.TickerVO;
import za.co.jacon.btca.exchange.btce.model.TransactionVO;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the BTC-e api.
 */
public class BtceApiImpl implements BtceApi {

    private static final Logger LOGGER = Logger.getLogger(BtceApiImpl.class);
    private static final String API_URL = "https://btc-e.com/api/3";

    /**
     * Instance of the configured restoperations.
     */
    private final RestOperations rest;
    private final ObjectMapper mapper;

    /**
     * Class constructor.
     *
     * Allows for injecting the restoperations instance..
     *
     * @param rest restoperations instance.
     */
    public BtceApiImpl(final RestOperations rest, ObjectMapper mapper) {
        this.rest = rest;
        this.mapper = mapper;
    }

    @Override
    public TickerVO ticker() {
        ResponseEntity<String> response = this.rest.getForEntity(API_URL + "/ticker/btc_usd", String.class);

        TickerVO tickerVO = null;
        try {
            JsonNode rootNode = mapper.readValue(response.getBody(), JsonNode.class);
            JsonNode jsonNode = rootNode.get("btc_usd");

            tickerVO = mapper.readValue(jsonNode.toString(), TickerVO.class);
        } catch (IOException e) {
            LOGGER.error("Unable to convert ticker json data to Ticker POJO. " + e.getMessage());
        }

        return tickerVO;
    }

    @Override
    public List<TransactionVO> getListOfLatestTransactions() {
        ResponseEntity<String> response = this.rest.getForEntity(API_URL + "/trades/btc_usd", String.class);

        List<TransactionVO> transactions = null;
        try {
            JsonNode rootNode = mapper.readValue(response.getBody(), JsonNode.class);
            JsonNode jsonNode = rootNode.get("btc_usd");

            TransactionVO[] transactionArr = mapper.readValue(jsonNode.toString(), TransactionVO[].class);

            transactions = Arrays.asList(transactionArr);

            Collections.sort(transactions, (tx1, tx2) -> (tx1.getTimestamp() > tx2.getTimestamp()) ? 1 : 0);
        } catch (IOException e) {
            LOGGER.error("Unable to convert ticker json data to Ticker POJO. " + e.getMessage());
        }

        return transactions;
    }

    @Override
    public TransactionVO getLatestTransaction() {
        List<TransactionVO> transactions = getListOfLatestTransactions();
        if (!transactions.isEmpty()) {
            return transactions.get(0);
        }
        return null;
    }
}
