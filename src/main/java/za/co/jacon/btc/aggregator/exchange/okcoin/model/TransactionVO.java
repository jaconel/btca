package za.co.jacon.btc.aggregator.exchange.okcoin.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Represents a transaction in the OKCoin exchange.
 */
public class TransactionVO extends za.co.jacon.btc.aggregator.model.TransactionVO {

    @JsonCreator
    public TransactionVO(
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("date_ms") long timestamp,
            @JsonProperty("price") BigDecimal price) {

        super(price, amount, timestamp);

    }
}
