package com.ces.cloudnote.app.location;

import java.io.BufferedReader;
import java.io.InputStreamReader;  
 
import org.apache.http.HttpEntity;  
import org.apache.http.HttpHost;  
import org.apache.http.HttpResponse;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.conn.params.ConnRouteParams;  
import org.apache.http.entity.StringEntity;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.params.HttpConnectionParams;  
import org.json.JSONArray;  
import org.json.JSONObject;  
 
import android.content.Context;  
import android.database.Cursor;  
import android.net.ConnectivityManager;  
import android.net.NetworkInfo.State;  
import android.net.Uri;  
import android.net.wifi.WifiManager;  
import android.telephony.TelephonyManager;  
import android.telephony.gsm.GsmCellLocation;  
import android.util.Log;  
 
/**  
 * ******************************************<br>  
 * 描述::GPS信息获取<br>  
 * @version 2.0<br>  
 ********************************************   
 */ 
public class GPSLocation {  
    private static int postType = -1;  
    public static final int DO_3G = 0;  
    public static final int DO_WIFI = 1;  
    public static final int NO_CONNECTION = 2;  
 
    /**  
     * 网络方式检查  
     */ 
    private static int netCheck(Context context) {  
        ConnectivityManager conMan = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)  
                .getState();  
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)  
                .getState();  
        if (wifi.equals(State.CONNECTED)) {  
            return DO_WIFI;  
        } else if (mobile.equals(State.CONNECTED)) {  
            return DO_3G;  
        } else {  
            return NO_CONNECTION;  
        }  
    }  
 
    /**  
     * 获取WifiManager获取信息  
     */ 
    private static JSONObject getInfoByWifiManager(Context context)  
            throws Exception {  
        JSONObject holder = new JSONObject();  
        holder.put("version", "1.1.0");  
        holder.put("host", "maps.google.com");  
        holder.put("address_language", "zh_CN");  
        holder.put("request_address", true);  
 
        WifiManager wifiManager = (WifiManager) context  
                .getSystemService(Context.WIFI_SERVICE);  
 
        if (wifiManager.getConnectionInfo().getBSSID() == null) {  
            throw new RuntimeException("bssid is null");  
        }  
 
        JSONArray array = new JSONArray();  
        JSONObject data = new JSONObject();  
        data.put("mac_address", wifiManager.getConnectionInfo().getBSSID());  
        data.put("signal_strength", 8);  
        data.put("age", 0);  
        array.put(data);  
        holder.put("wifi_towers", array);  
        return holder;  
    }  
 
    /**  
     * 获取TelephoneManager获取信息  
     */ 
    private static JSONObject getInfoByTelephoneManager(Context context)  
            throws Exception {  
        JSONObject holder = new JSONObject();  
        holder.put("version", "1.1.0");  
        holder.put("host", "maps.google.com");  
        holder.put("address_language", "zh_CN");  
        holder.put("request_address", true);  
        TelephonyManager tm = (TelephonyManager) context  
                .getSystemService(Context.TELEPHONY_SERVICE);  
        GsmCellLocation gcl = (GsmCellLocation) tm.getCellLocation();  
        int cid = gcl.getCid();  
        int lac = gcl.getLac();  
        int mcc = Integer.valueOf(tm.getNetworkOperator().substring(0, 3));  
        int mnc = Integer.valueOf(tm.getNetworkOperator().substring(3, 5));  
        JSONArray array = new JSONArray();  
        JSONObject data = new JSONObject();  
        data.put("cell_id", cid);  
        data.put("location_area_code", lac);  
        data.put("mobile_country_code", mcc);  
        data.put("mobile_network_code", mnc);  
        array.put(data);  
        holder.put("cell_towers", array);  
        return holder;  
    }  
 
    /**  
     * 通过wifi获取GPS信息  
     */ 
    private static HttpResponse connectionForGPS(Context context)  
            throws Exception {  
        HttpResponse httpResponse = null;  
        postType = netCheck(context);  
        if (postType == NO_CONNECTION) {  
            return null;  
        } else {  
            if (postType == DO_WIFI) {  
                if ((httpResponse = doOrdinary(context,  
                        getInfoByWifiManager(context))) != null) {  
                    return httpResponse;  
                } else if ((httpResponse = doAPN(context,  
                        getInfoByWifiManager(context))) != null) {  
                    return httpResponse;  
                } else if ((httpResponse = doOrdinary(context,  
                        getInfoByTelephoneManager(context))) != null) {  
                    return httpResponse;  
                } else if ((httpResponse = doAPN(context,  
                        getInfoByTelephoneManager(context))) != null) {  
                    return httpResponse;  
                }  
            }  
            if (postType == DO_3G) {  
                if ((httpResponse = doOrdinary(context,  
                        getInfoByTelephoneManager(context))) != null) {  
                    return httpResponse;  
                } else if ((httpResponse = doAPN(context,  
                        getInfoByTelephoneManager(context))) != null) {  
                    return httpResponse;  
                }  
            }  
            return null;  
        }  
    }  
 
    /**  
     * 通过普通方式定位  
     */ 
    private static HttpResponse doOrdinary(Context context, JSONObject holder) {  
        HttpResponse response = null;  
        try {  
            HttpClient httpClient = new DefaultHttpClient();  
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),  
                    20 * 1000);  
            HttpConnectionParams  
                    .setSoTimeout(httpClient.getParams(), 20 * 1000);  
            HttpPost post = new HttpPost("http://74.125.71.147/loc/json");  
            StringEntity se = new StringEntity(holder.toString());  
            post.setEntity(se);  
            response = httpClient.execute(post);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
        return response;  
    }  
 
    /**  
     * 通过基站定位  
     */ 
    private static HttpResponse doAPN(Context context, JSONObject holder) {  
        HttpResponse response = null;  
        try {  
            HttpClient httpClient = new DefaultHttpClient();  
            HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),  
                    20 * 1000);  
            HttpConnectionParams  
                    .setSoTimeout(httpClient.getParams(), 20 * 1000);  
            HttpPost post = new HttpPost("http://64.233.160.136/loc/json");
            // 设置代理  
            Uri uri = Uri.parse("content://telephony/carriers/preferapn"); // 获取当前正在使用的APN接入点  
            Cursor mCursor = context.getContentResolver().query(uri, null,  
                    null, null, null);  
            if (mCursor != null) {  
                if (mCursor.moveToFirst()) {  
                    String proxyStr = mCursor.getString(mCursor  
                            .getColumnIndex("proxy"));  
                    if (proxyStr != null && proxyStr.trim().length() > 0) {  
                        HttpHost proxy = new HttpHost(proxyStr, 80);  
                        httpClient.getParams().setParameter(  
                                ConnRouteParams.DEFAULT_PROXY, proxy);  
                    }  
                }  
            }  
            StringEntity se = new StringEntity(holder.toString());  
            post.setEntity(se);  
            response = httpClient.execute(post);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
        return response;  
    }  
 
    /**  
     * GPS信息解析  
     */ 
    public static LMLocation getGPSInfo(Context context) throws Exception {  
        HttpResponse response = connectionForGPS(context);  
        if (response == null) {  
            Log.d("GPSLocation", "response == null");  
            return null;  
        }  
        LMLocation location = null;  
        if (response.getStatusLine().getStatusCode() == 200) {  
            location = new LMLocation();  
            HttpEntity entity = response.getEntity();  
            BufferedReader br;  
            try {  
                br = new BufferedReader(new InputStreamReader(  
                        entity.getContent()));  
                StringBuffer sb = new StringBuffer();  
                String result = br.readLine();  
                while (result != null) {  
                    sb.append(result);  
                    result = br.readLine();  
                }  
                JSONObject json = new JSONObject(sb.toString());  
                JSONObject lca = json.getJSONObject("location");  
 
                location.setAccess_token(json.getString("access_token"));  
                if (lca != null) {  
                    if (lca.has("accuracy"))  
                        location.setAccuracy(lca.getString("accuracy"));  
                    if (lca.has("longitude"))  
                        location.setLatitude(lca.getDouble("longitude"));
                    if (lca.has("latitude"))  
                        location.setLongitude(lca.getDouble("latitude"));  
                    if (lca.has("address")) {  
                        JSONObject address = lca.getJSONObject("address");  
                        if (address != null) {  
                            if (address.has("region"))  
                                location.setRegion(address.getString("region"));  
                            if (address.has("street_number"))  
                                location.setStreet_number(address  
                                        .getString("street_number"));  
                            if (address.has("country_code"))  
                                location.setCountry_code(address  
                                        .getString("country_code"));  
                            if (address.has("street"))  
                                location.setStreet(address.getString("street"));  
                            if (address.has("city"))  
                                location.setCity(address.getString("city"));  
                            if (address.has("country"))  
                                location.setCountry(address  
                                        .getString("country"));  
                        }  
                    }  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
                location = null;  
            }  
        }  
        return location;  
    }  
 
} 