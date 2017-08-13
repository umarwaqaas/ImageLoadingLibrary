package NetworkRequest;

import org.json.JSONArray;

import java.util.ArrayList;

import DataRequest.DataLoader;
import Params.HeaderParameter;
import Params.RequestParameter;
import Params.Response;
import RequestListener.NetworkRequestListener;


/**
 * Created by Umer Waqas on 11/08/2017.
 */
public class JsonArrayNetworkRequest extends BaseNetworkRequest<String, Void, JSONArray> {
    private DataLoader.Method mMethod;
    private String mUrl;
    private NetworkRequestListener<JSONArray> mCallback;
    private boolean error=false;
    private Exception exception=null;
    private ArrayList<RequestParameter> params;
    private ArrayList<HeaderParameter> headers;


    public JsonArrayNetworkRequest(DataLoader.Method method, String url, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers, NetworkRequestListener<JSONArray> callback){
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
    protected JSONArray doInBackground(String... urls) {
        try {

            Response response=makeRequest(mUrl,mMethod,params,headers);
            JSONArray json= new JSONArray(response.getDataAsString());
            if(this.mCacheManager!=null){
                if(this.mCacheManager.ReturnDataFromCache(mUrl)==null)
                    this.mCacheManager.addNewDataInCache(mUrl,json);
            }
            return json;

        } catch (Exception e) {
            e.printStackTrace();
            exception  =e;
            error=true;
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONArray data) {
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
