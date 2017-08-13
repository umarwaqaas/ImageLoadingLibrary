package Cache;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.LruCache;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Umer Waqas on 10/08/2017.
 */
public class DataCacheManager<T> extends LruCache<String, T> implements DataCacheInterface<T> {
    //Check time to clear cache
    private HashMap<String, Long> cacheTimeoutTimeStamp = new HashMap<>();

    // cache will evicted after 10 minutes
    private final int TIME_OUT = 10 * 1000 * 60;


    /**
     * Constructor
     *
     * @param dateCacheSize Integer, size in bytes
     */
    public DataCacheManager(int dateCacheSize) {
        super(dateCacheSize);
    }

    @Override
    protected int sizeOf(String keyValue, T data) {
        // T define type of data i.e Image , Json etc
        int bytesCount;
        if (data instanceof Bitmap) {
            bytesCount = ((Bitmap) data).getByteCount();
        } else if (data instanceof JSONObject) {
            bytesCount = ((JSONObject) data).toString().getBytes().length;
        } else {
            bytesCount = ((JSONArray) data).toString().getBytes().length;
        }

        return bytesCount / 1024;

    }

    /**
     * Adds data to memory cache
     *
     * @param keyValue String key to identify cache resource
     * @param data     Data to be stored in cache
     */
    @Override
    public void addNewDataInCache(String keyValue, T data) {
        // T define type of data i.e Image , Json etc
        if (ReturnDataFromCache(keyValue) == null) {
            synchronized (this) {
                put(keyValue, data);
                cacheTimeoutTimeStamp.put(keyValue, SystemClock.uptimeMillis()); //count to 0 when added
            }
        }
    }


    /**
     * Evict Data From Cache
     *
     * @param keyValue identifier to each single url
     */
    @Override
    public void evictDataFromCache(String keyValue) {
        if (ReturnDataFromCache(keyValue) != null) {
            synchronized (this) {
                remove(keyValue);
            }
        }
    }

    /**
     * Gets cached data
     *
     * @param key identifier to each single url
     * @return data
     */
    @Override
    public T ReturnDataFromCache(String key) {
        synchronized (this) {
            cacheTimeoutTimeStamp.put(key, SystemClock.uptimeMillis());
            removeUnusedCache();
        }
        return get(key);
    }

    /**
     * Removes unused items
     *
     */
    @Override
    public void removeUnusedCache() {
        Map<String, T> items = snapshot();
        for (String key : items.keySet()) {
            long cacheTime = cacheTimeoutTimeStamp.get(key);
            if (cacheTime + TIME_OUT < SystemClock.uptimeMillis()) {
                remove(key);
            }

        }
    }


}



