package za.co.jacon.btca.exchange.okcoin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import za.co.jacon.btca.exchange.okcoin.model.TransactionVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the OKCoin api.
 */
public class OKCoinApiImpl implements OKCoinApi {

    private static final Logger LOGGER = Logger.getLogger(OKCoinApiImpl.class);
    private final ObjectMapper mapper;
    private final RestOperations rest;

    private static final String API_URL = "https://www.okcoin.cn/api";

    /**
     * Class constructor.
     * @param mapper the object mapper for json deserialization
     */
    public OKCoinApiImpl(RestOperations rest, ObjectMapper mapper) {
        this.rest = rest;
        this.mapper = mapper;
    }

    @Override
    public List<TransactionVO> getListOfLatestTransactions() {
        ResponseEntity<String> response = this.rest.getForEntity(API_URL + "/trades.do?ok=1", String.class);

        List<TransactionVO> transactions = new ArrayList<>();
        try {
            TransactionVO[] transactionsArr = mapper.readValue(response.getBody(), TransactionVO[].class);
            transactions = Arrays.asList(transactionsArr);

            Collections.sort(transactions, (tx1, tx2) -> (tx1.getTimestamp() > tx2.getTimestamp()) ? 1 : 0);
        } catch (IOException e) {
            LOGGER.error("Error occurred retrieving transaction data from OKCoin exchange: " + e.getMessage());
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
