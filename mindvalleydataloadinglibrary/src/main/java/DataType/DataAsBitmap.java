package DataType;

import android.graphics.Bitmap;

import java.util.ArrayList;

import BaseRequest.RequestType;
import Cache.DataCacheInterface;
import DataRequest.DataLoader;
import NetworkRequest.ImageNetworkRequest;
import Params.HeaderParameter;
import Params.RequestParameter;
import RequestListener.NetworkRequestListener;


/**
 * Created by Umer Waqas on 11/08/2017.
 */
public class DataAsBitmap extends RequestType<Bitmap> {
    private String mUrl;
    private NetworkRequestListener<Bitmap> mListener;
    private DataLoader.Method mMethod;

    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;
    private ImageNetworkRequest mTask;
    private DataCacheInterface<Bitmap> mCacheManager;

    /**
     * Constructor to load Image DataType
     * @param url
     * @param params
     * @param headers
     */
    public DataAsBitmap(DataLoader.Method m, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers){
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
    public DataAsBitmap setRequestCallback(NetworkRequestListener<Bitmap> listener){
        this.mListener=listener;
        mListener.onRequestStart();
        Bitmap data;
        if(mCacheManager!=null) {
            data = mCacheManager.ReturnDataFromCache(mUrl);
            if (data != null) {
                mListener.onDataArrive(data);
                return this;
            }
        }

        mTask = new ImageNetworkRequest(mMethod, mUrl, params, headers, mListener);
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
    public DataAsBitmap setDataCacheManager(DataCacheInterface<Bitmap> cache){
        this.mCacheManager=cache;
        return this;
    }


}
