package za.co.jacon.btca.distributor.mongo;

import com.mongodb.DB;
import za.co.jacon.btca.distributor.Distributor;
import za.co.jacon.btca.model.TransactionVO;

/**
 * The Mongodb distributor. Used to store statistics in the database
 */
public abstract class MongoDistributor implements Distributor<TransactionVO> {

    /**
     * The mongodb client.
     */
    protected final DB mongodb;

    /**
     * Class constructor.
     * @param mongodb the mongodb client
     */
    public MongoDistributor(final DB mongodb) {
        this.mongodb = mongodb;
    }
}
