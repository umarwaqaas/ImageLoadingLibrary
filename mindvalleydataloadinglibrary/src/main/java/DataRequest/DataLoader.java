package DataRequest;

import android.content.Context;

import java.util.ArrayList;

import DataType.DataAsJsonObject;
import Params.HeaderParameter;
import Params.RequestParameter;
import DataType.DataAsBitmap;
import DataType.DataAsJsonArray;


/**
 * Created by Umer Waqas on 11/08/2017.
 */
public class DataLoader {
    public static enum Method {
        GET,
        POST,
        PUT,
        DELETE
    }

    private Context mContext;
    private String mUrl;
    private DataLoader.Method mMethod;
    private static DataLoader instance = null;

    private ArrayList<RequestParameter> params = new ArrayList<>();
    private ArrayList<HeaderParameter> headers = new ArrayList<>();

    public static DataLoader with(Context c) {
        return getInstance(c);
    }

    /**
     * Constructor
     *
     * @param c it is the context
     */
    public DataLoader(Context c) {
        this.mContext = c;
    }

    /**
     * Returns singleton instance
     * @param context
     * @return Singleton instance
     */
    public static DataLoader getInstance(Context context) {
        if (context == null)
            throw new NullPointerException("Can not pass null context in to retrieve DataRequest.DataLoader instance");
        synchronized (DataLoader.class) {
            if (instance == null)
                instance = new DataLoader(context);
        }

        return instance;
    }


    public DataLoader load(DataLoader.Method m, String url) {
        this.mUrl = url;
        this.mMethod = m;
        return this;
    }

    /**
     * Sets type of request
     *
     * @return RequestType as Json
     */
    public DataAsJsonObject asJsonObject() {
        return new DataAsJsonObject(mMethod, mUrl, params, headers);
    }

    /**
     * Sets type of request
     *
     * @return  RequestType as Json Array
     */
    public DataAsJsonArray asJsonArray() {
        return new DataAsJsonArray(mMethod, mUrl, params, headers);
    }


    /**
     * Sets type of request
     *
     * @return RequestType as Bitmap or Image
     */
    public DataAsBitmap asImage() {
        return new DataAsBitmap(mMethod, mUrl, params, headers);
    }


    /**
     * Sets request body parameters
     *
     * @param key   Parameter key
     * @param value Parameter value
     * @return DataRequest.DataLoader instance
     */
    public DataLoader setRequestParameter(String key, String value) {
        RequestParameter param = new RequestParameter();
        param.setKey(key);
        param.setValue(value);
        this.params.add(param);
        return this;
    }


    /**
     * Sets request header parameters
     *
     * @param key   Parameter key
     * @param value Parameter value
     * @return DataRequest.DataLoader instance
     */
    public DataLoader setHeaderParameter(String key, String value) {
        HeaderParameter param = new HeaderParameter();
        param.setKey(key);
        param.setValue(value);
        this.headers.add(param);
        return this;
    }


}
