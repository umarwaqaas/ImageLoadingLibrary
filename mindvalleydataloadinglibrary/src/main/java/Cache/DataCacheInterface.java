package Cache;

/**
 * Created by Umer Waqas on 10/08/2017.
 */
public interface DataCacheInterface<Z> {
    public void addNewDataInCache(String keyValue, Z data);
    public void evictDataFromCache(String keyValue);
    public Z ReturnDataFromCache(String keyValue);
    public void removeUnusedCache();

}
