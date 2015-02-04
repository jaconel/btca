package za.co.jacon.btc.aggregator.distributor;

/**
 * The distributor interface.
 */
public interface Distributor<T> {

    public void distribute(T dataObject, String exchange);
}
