package za.co.jacon.btca.distributor;

/**
 * The distributor interface.
 */
public interface Distributor<T> {

    public void distribute(T dataObject, String exchange);
}
