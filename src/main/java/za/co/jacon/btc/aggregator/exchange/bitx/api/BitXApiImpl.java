package za.co.jacon.btc.aggregator.exchange.bitx.api;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestOperations;
import za.co.jacon.btc.aggregator.exchange.bitx.model.TickerVO;
import za.co.jacon.btc.aggregator.exchange.bitx.model.TransactionVO;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of the BitXApi responsible for integration with the BitX exchange.
 */
public class BitXApiImpl implements BitXApi {

    private final Logger LOGGER = Logger.getLogger(BitXApiImpl.class);
    private final RestOperations rest;
    private final ObjectMapper mapper;

    private static final String API_URL = "https://api.mybitx.com/api/1";

    /**
     * Class constructor.
     * @param rest the rest operations instance
     * @param objectMapper the object mapper instance
     */
    public BitXApiImpl(final RestOperations rest, final ObjectMapper objectMapper) {
        this.rest = rest;
        this.mapper = objectMapper;
    }

    @Override
    public TickerVO ticker() {
        TickerVO tickerVO = null;
        try {
            tickerVO = this.rest.getForObject(API_URL + "/ticker?pair=XBTUSD", TickerVO.class);
        } catch (HttpMessageNotReadableException ex) {
            LOGGER.error("Unable to deserialize the bitx ticker api response. It might be that the api returned and error. Original exception was: " + ex.getMessage());
        }
        return tickerVO;
    }

    @Override
    public List<TransactionVO> getListOfLatestTransactions() {
        ResponseEntity<String> response = this.rest.getForEntity(API_URL + "/trades?pair=XBTZAR", String.class);

        List<TransactionVO> transactions = null;
        try {
            JsonNode rootNode = mapper.readValue(response.getBody(), JsonNode.class);
            JsonNode jsonNode = rootNode.get("trades");

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
