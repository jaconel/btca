package za.co.jacon.btc.aggregator.exchange.distributor;

/**
 * The distributor interface.
 */
public interface Distributor {

    public void distribute(Object dataObject, String exchange);
}
