package DataType;

import org.json.JSONArray;

import java.util.ArrayList;

import BaseRequest.RequestType;
import Cache.DataCacheInterface;
import DataRequest.DataLoader;
import NetworkRequest.JsonArrayNetworkRequest;
import Params.HeaderParameter;
import Params.RequestParameter;
import RequestListener.NetworkRequestListener;


/**
 * Created by Umer Waqas on 11/08/2017.
 */
public class DataAsJsonArray extends RequestType<JSONArray> {
    private String mUrl;
    private NetworkRequestListener<JSONArray> mListener;
    private DataLoader.Method mMethod;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;
    private JsonArrayNetworkRequest mTask;
    private DataCacheInterface<JSONArray> mCacheManager;

    /**
     * Constructor to load Json Array
     * @param url
     * @param params
     * @param headers
     */
    public DataAsJsonArray(DataLoader.Method m, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers){
        this.mUrl=url;
        this.mMethod=m;
        this.headers=headers;
        this.params=params;
    }

    /**
     *
     * Sets Network callback after Http response is received
     * @param listener
     */
    public DataAsJsonArray setRequestCallback(NetworkRequestListener<JSONArray> listener){
        this.mListener=listener;
        mListener.onRequestStart();
        JSONArray data;
        if(mCacheManager!=null) {
            data = mCacheManager.ReturnDataFromCache(mUrl);
            if (data != null) {
                mListener.onDataArrive(data);
                return this;
            }
        }

        mTask = new JsonArrayNetworkRequest(mMethod, mUrl, params, headers, mListener);
        mTask.setCacheManager(mCacheManager);
        mTask.execute();
        return this;
    }

    /**
     * Cancels the current running network request
     * @return true if request cancelled
     */
    public boolean cancelRequest(){
        if(mTask!=null){
            mTask.cancel(true);
            if(mTask.isCancelled()) {
                mListener.onCancelRequest();
                return true;
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    /**
     * Sets DataCacheManager for this
     * @param cache for Image
     * @return DataAsJsonObject
     */
    public DataAsJsonArray setDataCacheManager(DataCacheInterface<JSONArray> cache){
        this.mCacheManager=cache;
        return this;
    }


}
