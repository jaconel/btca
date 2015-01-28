package za.co.jacon.btc.aggregator.exchange.bitfinex;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestOperations;
import za.co.jacon.btc.aggregator.exchange.bitfinex.vo.TickerVO;
import za.co.jacon.btc.aggregator.exchange.bitfinex.vo.TransactionVO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the bitfinex api.
 */
public class BitfinexApiImpl implements BitfinexApi {

    /**
     * Configured instace of the rest operations.
     */
    private final RestOperations rest;

    /**
     * Application logger. Instance of the log4j logger.
     */
    private static final Logger LOGGER = Logger.getLogger(BitfinexApiImpl.class);

    /**
     * The uri to the bitfinex api.
     */
    private static final String API_URL = "https://api.bitfinex.com/v1";

    /**
     * The object mapper used for deserialization.
     */
    private final ObjectMapper mapper;

    /**
     * The symbol for which we are retrieving information.
     * TODO: We need to move this out at some point.
     */
    private static final String SYMBOL = "btcusd";

    /**
     * Class constructor.
     *
     * @param rest the rest operations instance
     */
    public BitfinexApiImpl(RestOperations rest, ObjectMapper mapper) {
        this.rest = rest;
        this.mapper = mapper;
    }

    @Override
    public TickerVO ticker() {
        LOGGER.info("Calling Bitfinex api to retrieve ticker data");

        TickerVO tickerVO = null;
        try {
            tickerVO = this.rest.getForObject(API_URL + "/pubticker/" + SYMBOL, TickerVO.class);
        } catch (HttpMessageNotReadableException ex) {
            LOGGER.error("Unable to deserialize the bitx ticker api response. It might be that the api returned and error. Original exception was: " + ex.getMessage());
        }
        return tickerVO;
    }

    public TransactionVO getLatestTransaction() {
        List<TransactionVO> transactions = getListOfLatestTransactions();
        if (!transactions.isEmpty()) {
            return transactions.get(0);
        }
        return null;
    }

    @Override
    public List<TransactionVO> getListOfLatestTransactions() {
        ResponseEntity<TransactionVO[]> response = this.rest.getForEntity(API_URL + "/trades/" + SYMBOL, TransactionVO[].class);
        if (response.getStatusCode() != HttpStatus.OK) {
            LOGGER.error("Non 200 OK status code received for retrieval of trades from bitfinex.");
        }
        List<TransactionVO> transactions = Arrays.asList(response.getBody());
        Collections.sort(transactions, (tx1, tx2) -> (tx1.getTimestamp() > tx2.getTimestamp()) ? 1: 0);

        return transactions;
    }
}
