package RequestListener;

/**
 * Created by Umer Waqas on 12/08/2017.
 */
public interface NetworkRequestListener<Z> {
    /**
     * onStart of Network Request
     */
    public void onRequestStart();

    /**
     * Once the data arrive as response of calling web service
     */
    public void onDataArrive(Z data);

    /**
     * in case of network request failed  , return related Exception
     *  @param e   Parameter key
     */
    public void onErrorMessage(Exception e);

    /**
     * In case User cancel the network request.
     */
    public void onCancelRequest();

}
