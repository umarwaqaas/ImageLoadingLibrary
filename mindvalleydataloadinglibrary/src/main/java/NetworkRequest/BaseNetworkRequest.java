package NetworkRequest;

import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import Cache.DataCacheInterface;
import DataRequest.DataLoader;
import Params.HeaderParameter;
import Params.RequestParameter;
import Params.Response;


/**
 * Created by Umer Waqas on 11/08/2017.
 */
public abstract class BaseNetworkRequest<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    final String TAG = getClass().getCanonicalName();
    protected DataCacheInterface<Result> mCacheManager;
    static final int CONNECTION_READ_TIMEOUT = 15000;
    static final int CONNECTION_RESPONSE_TIMEOUT = 15000;
    HttpURLConnection CONNECTION;

    protected Response makeRequest(String url, DataLoader.Method m, ArrayList<RequestParameter> params, ArrayList<HeaderParameter> headers) throws IOException {
        InputStream is = null;
        URL mUrl = new URL(url);
        CONNECTION = (HttpURLConnection) mUrl.openConnection();
        CONNECTION.setReadTimeout(CONNECTION_READ_TIMEOUT);
        CONNECTION.setConnectTimeout(CONNECTION_RESPONSE_TIMEOUT);
        if (m == DataLoader.Method.GET) {
            CONNECTION.setRequestMethod("GET");
        } else if (m == DataLoader.Method.POST) {
            CONNECTION.setRequestMethod("POST");
        } else if (m == DataLoader.Method.DELETE) {
            CONNECTION.setRequestMethod("DELETE");
        } else if (m == DataLoader.Method.PUT) {
            CONNECTION.setRequestMethod("PUT");
        }
        CONNECTION.setDoInput(true);
        CONNECTION.setDoOutput(m != DataLoader.Method.GET);


        if (headers.size() > 0) {
            for (HeaderParameter header : headers) {
                CONNECTION.setRequestProperty(header.getKey(), header.getValue());
            }
        }


        Uri.Builder builder = new Uri.Builder();

        //write request parameters
        if (params.size() > 0) {
            for (RequestParameter requestParameter : params) {
                builder.appendQueryParameter(requestParameter.getKey(), requestParameter.getValue());
            }
            String query = builder.build().getEncodedQuery();
            OutputStream os = CONNECTION.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
        }
        CONNECTION.connect();
        int response = CONNECTION.getResponseCode();
        is = CONNECTION.getInputStream();

        Response resp = new Response();
        resp.setCode(response);
        resp.setData(is);
        return resp;
    }


    public void setCacheManager(DataCacheInterface<Result> cacheManager) {
        this.mCacheManager = cacheManager;
    }


}
