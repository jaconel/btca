package za.co.jacon.btca.distributor.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import za.co.jacon.btca.model.TransactionVO;

/**
 * Stores the price information in a mongo collection.
 */
public class MongoPriceDistributor extends MongoDistributor {

    private static final String COLLECTION = "prices";

    /**
     * Reference to mongodb collection.
     */
    private final DBCollection collection;

    /**
     * Class constructor.
     *
     * @param mongodb the mongodb client
     */
    public MongoPriceDistributor(final DB mongodb) {
        super(mongodb);

        collection = mongodb.getCollection(COLLECTION);
    }

    @Override
    public void distribute(final TransactionVO transaction, final String exchange) {
        // refactor this code to work via reflection maybe? I just don't like the way this works at the moment.
        BasicDBObject doc = new BasicDBObject("exchange", exchange);
        doc.append("accumulation_timestamp", transaction.getAccumulationTimestamp());
        doc.append("timestamp", transaction.getTimestamp());
        doc.append("price", transaction.getPrice().doubleValue());

        collection.insert(doc);
    }
}
