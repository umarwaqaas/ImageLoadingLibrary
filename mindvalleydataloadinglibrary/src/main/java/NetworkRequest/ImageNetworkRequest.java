package NetworkRequest;

import android.graphics.Bitmap;

import java.util.ArrayList;

import DataRequest.DataLoader;
import Params.HeaderParameter;
import Params.RequestParameter;
import Params.Response;
import RequestListener.NetworkRequestListener;


/**
 * Created by Umer Waqas on 11/08/2017.
 */
public class ImageNetworkRequest extends BaseNetworkRequest<String, Void, Bitmap> {
    private DataLoader.Method mMethod;
    private String mUrl;
    private NetworkRequestListener<Bitmap> mCallback;
    private boolean error=false;
    private Exception exception=null;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;

    public ImageNetworkRequest(DataLoader.Method method, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers, NetworkRequestListener<Bitmap> callback){
        this.mUrl=url;
        this.mMethod=method;
        this.mCallback=callback;
        this.params=params;
        this.headers=headers;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        try {
            Response response=makeRequest(mUrl,mMethod,params,headers);
            Bitmap bitmap= response.getAsBitmap();
            if(this.mCacheManager!=null){
                if(this.mCacheManager.ReturnDataFromCache(mUrl)==null)
                    this.mCacheManager.addNewDataInCache(mUrl,bitmap);
            }
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            exception=e;
            error=true;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap data) {
        super.onPostExecute(data);
        if(!error)
            this.mCallback.onDataArrive(data);
        else
            this.mCallback.onErrorMessage(exception);
    }

    /**
     * Sometimes users may cancel
     */
    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(this.mCacheManager!=null){
            this.mCacheManager.evictDataFromCache(mUrl);
        }
    }
}
