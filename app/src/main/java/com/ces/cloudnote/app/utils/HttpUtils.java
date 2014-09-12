package com.ces.cloudnote.app.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Http请求的类
 * Created by remex on 14-7-16.
 */
public class HttpUtils {

    public static final String CHARSET = "UTF-8";

    /**
     * Get method
     * @param mQueue
     * @param url
     * @param listener
     * @param errorListener
     * @param params
     */
    public static void get(RequestQueue mQueue, String url, Response.Listener<String> listener,
                           Response.ErrorListener errorListener, final Map<String, String> params){
        mQueue.add(new StringRequest(Request.Method.GET,
                getUrlWithParams(url, params),
                listener, errorListener));
    }

    /**
     * Get method with header
     * @param mQueue
     * @param url
     * @param listener
     * @param errorListener
     * @param params
     * @param header
     */
    public static void get(RequestQueue mQueue, String url, Response.Listener<String> listener,
                           Response.ErrorListener errorListener, final Map<String, String> params,
                           final Map<String, String> header){
        mQueue.add(new StringRequest(Request.Method.GET,
                getUrlWithParams(url, params),
                listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }
        });
    }

    /**
     * Post method
     * @param mQueue
     * @param url
     * @param listener
     * @param errorListener
     * @param params
     * @param header
     */
    public static void post(RequestQueue mQueue, String url,
                            Response.Listener<String> listener,
                            Response.ErrorListener errorListener,
                            final Map<String, String> params,
                            final Map<String, String> header
    ){
        mQueue.add(new StringRequest(Request.Method.POST,
                url, listener, errorListener){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        });
    }

    /**
     * Base url append params
     * @param baseUrl
     * @param params
     * @return
     */
    private static String getUrlWithParams(String baseUrl, Map<String, String> params) {
        if(params == null || params.size() == 0)
            return baseUrl;
        StringBuilder sb = new StringBuilder();
        for(String paramKey : params.keySet()){
            try {
                sb.append("&").append(paramKey).append("=").append(URLEncoder.encode(params.get(paramKey),CHARSET));
            } catch (UnsupportedEncodingException e) {
                LogUtils.e(e.toString());
                return baseUrl;
            }catch (Exception e){
                LogUtils.e(e.toString());
                return baseUrl;
            }
        }
        LogUtils.d(baseUrl + sb.replace(0,1,"?").toString());
        return baseUrl + sb.replace(0,1,"?").toString();
    }

}
