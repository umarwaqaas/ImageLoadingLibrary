package BaseRequest;


import Cache.DataCacheInterface;
import RequestListener.NetworkRequestListener;

/**
 * Created by Umer Waqas on 09/08/2017.
 */
public abstract class RequestType<Z> {
    public abstract RequestType setDataCacheManager(DataCacheInterface<Z> cacheManager);
    public abstract RequestType setRequestCallback(NetworkRequestListener<Z> callback);
    public abstract boolean cancelRequest();
}
