package com.ces.cloudnote.app.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Map;

/**
 *
 * Created by remex on 14-8-6.
 */
public class AsyncHttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        post(url, params, responseHandler, null);
    }

    public static void post(String url,
                             RequestParams params,
                             AsyncHttpResponseHandler responseHandler,
                             Map<String,String> header) {
        if(params != null) {
            for (String key : header.keySet()) {
                client.addHeader(key, header.get(key));
            }
        }
        client.post(url, params, responseHandler);
    }

}
