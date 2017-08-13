package DataType;

import org.json.JSONObject;

import java.util.ArrayList;

import BaseRequest.RequestType;
import Cache.DataCacheInterface;
import DataRequest.DataLoader;
import NetworkRequest.JsonObjectNetworkRequest;
import Params.HeaderParameter;
import Params.RequestParameter;
import RequestListener.NetworkRequestListener;


/**
 * Created by Umer Waqas on 11/08/2017.
 */
public class DataAsJsonObject extends RequestType<JSONObject> {
    private String mUrl;
    private NetworkRequestListener<JSONObject> mListener;
    private DataLoader.Method mMethod;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;
    private JsonObjectNetworkRequest mTask;
    private DataCacheInterface<JSONObject> mCacheManager;


    /**
     * Constructor to load Json
     * @param url
     * @param params
     * @param headers
     */
    public DataAsJsonObject(DataLoader.Method m, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers){
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
    public DataAsJsonObject setRequestCallback(NetworkRequestListener<JSONObject> listener){
        this.mListener=listener;
        mListener.onRequestStart();
        JSONObject data;
        if(mCacheManager!=null) {
            data = mCacheManager.ReturnDataFromCache(mUrl);
            if (data != null) {
                mListener.onDataArrive(data);
                return this;
            }
        }

        mTask=new JsonObjectNetworkRequest(mMethod,mUrl,params,headers,mListener);
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
    public DataAsJsonObject setDataCacheManager(DataCacheInterface<JSONObject> cache){
        this.mCacheManager=cache;
        return this;
    }


}
