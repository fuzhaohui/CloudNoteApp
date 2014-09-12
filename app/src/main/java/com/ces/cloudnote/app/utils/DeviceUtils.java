package com.ces.cloudnote.app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class DeviceUtils {

    /**
     * 手机系统信息
     *
     * @return
     */
    public static String getSystemInfo() {
        return android.os.Build.MODEL + " " + android.os.Build.VERSION.RELEASE;
    }

    /**
     * mac地址
     *
     * @param con
     * @return
     */
    public static String getLocalMacAddress(Context con) {
        WifiManager wifi = (WifiManager) con.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 获得应用版本名
     *
     * @param con
     * @return
     */
    public static String getVersionName(final Context con) {
        String version = "1.0";

        PackageManager packageManager = con.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(con.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            LogUtils.e(e.toString());
        }
        return version;
    }


    public static int getVersionCode(final Context con) {
        int version = 1;
        PackageManager packageManager = con.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(con.getPackageName(), 0);
            version = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            LogUtils.e(e.toString());
        }
        return version;
    }

    /**
     * 判断手机是否有可用网络
     *
     * @param ctx
     * @return
     */
    public static boolean isNetworkEnabled(Context ctx) {
        String state = checkNetworkInfo(ctx);
        if ("00".equals(state)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 检测WLAN是否有效
     *
     * @param ctx
     * @return 有效：true，无效：false
     */
    public static boolean isWifiNetwork(Context ctx) {
        String result = checkNetworkInfo(ctx);
        if ("01".equals(result) || "11".equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断手机网络是否可用
     *
     * @param ctx
     * @return
     */
    public static boolean isMobileNetwork(Context ctx) {
        String result = checkNetworkInfo(ctx);
        if ("10".equals(result) || "11".equals(result)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检测网络状态<br>
     * 返回一个"00"形式的字符串
     * <li>"00":3G无效，WLAN无效
     * <li>"10":3G有效，WLAN无效
     * <li>"01":3G无效，WLAN有效
     * <li>"11":3G有效，WLAN有效
     *
     * @param ctx Context
     */
    public static String checkNetworkInfo(Context ctx) {
        String g3 = "0";
        String wlan = "0";
        ConnectivityManager conMan = null;
        try {
            conMan = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        if (conMan == null) return "00";
        State mobile = null;
        State wifi = null;
        //mobile 3G Data Network
        NetworkInfo mobileInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileInfo != null) {
            mobile = mobileInfo.getState();
        }
        //wifi
        NetworkInfo wifiInfo = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null) {
            wifi = wifiInfo.getState();
        }
        if (mobile == State.CONNECTED)
            g3 = "1";
        if (wifi == State.CONNECTED)
            wlan = "1";
        //如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接        
        //ctx.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));//进入无线网络配置界面
        return g3 + wlan;
    }

    /**
     * 是否是高速网络
     *
     * @param ctx
     * @return
     */
    public static boolean is3GNetwork(Context ctx) {
        ConnectivityManager conMan = null;
        try {
            conMan = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        if (conMan == null) return false;
        NetworkInfo info = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (info != null) {
            /*
             * 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EDGE，电信的2G为CDMA，电信的3G为EVDO
             * 取值:
                TelephonyManager.NETWORK_TYPE_1xRTT;
                TelephonyManager.NETWORK_TYPE_CDMA;
                TelephonyManager.NETWORK_TYPE_EDGE;
                TelephonyManager.NETWORK_TYPE_EHRPD;
                TelephonyManager.NETWORK_TYPE_EVDO_0;
                TelephonyManager.NETWORK_TYPE_EVDO_A;
                TelephonyManager.NETWORK_TYPE_EVDO_B;
                TelephonyManager.NETWORK_TYPE_GPRS;
                TelephonyManager.NETWORK_TYPE_HSDPA;
                TelephonyManager.NETWORK_TYPE_HSPA;
                TelephonyManager.NETWORK_TYPE_HSPAP;
                TelephonyManager.NETWORK_TYPE_HSUPA;
                TelephonyManager.NETWORK_TYPE_IDEN;
                TelephonyManager.NETWORK_TYPE_LTE;
                TelephonyManager.NETWORK_TYPE_UMTS;
                TelephonyManager.NETWORK_TYPE_UNKNOWN;
            */
            int subtype = info.getSubtype();
            if (subtype == TelephonyManager.NETWORK_TYPE_1xRTT
                    || subtype == TelephonyManager.NETWORK_TYPE_CDMA
                    || subtype == TelephonyManager.NETWORK_TYPE_EDGE
                    || subtype == TelephonyManager.NETWORK_TYPE_GPRS
                    || subtype == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public static String getPhoneIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {

        }
        return "";
    }


    private static String getIMEI(Context ctx) {
        String imei = "";
        try {
            final TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
            imei = tm.getDeviceId();
        } catch (Exception e) {

        }
        return imei;
    }

    public static String getUuid(Context ctx) {
        UuidUtils u = new UuidUtils(ctx);
        return u.getDeviceUuid();
    }

    /**
     * @param ctx
     * @return
     */
    @Deprecated
    public static String getIMEIorMACAddress(Context ctx) {
        String uniqueId = "";
        uniqueId = getIMEI(ctx);
        if (TextUtils.isEmpty(uniqueId))
            uniqueId = getLocalMacAddress(ctx);
        LogUtils.e("uuid", uniqueId);
        return uniqueId;
    }

    public static String getPhoneNumber(Context ctx) {
        TelephonyManager phoneMgr = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = phoneMgr.getLine1Number();
        if (!TextUtils.isEmpty(phoneNumber) && phoneNumber.contains("+86"))
            phoneNumber = phoneNumber.replace("+86", "");
        return phoneNumber;
    }


    public static boolean getHasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static void sendSms(Context ctx, String phone) throws Exception {
        sendSms(ctx, phone, "");
    }

    public static void sendSms(Context ctx, String phone, String content) throws Exception {
        try {
            Uri uri = Uri.parse("smsto:" + phone);
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
            smsIntent.putExtra("sms_body", content);
            ctx.startActivity(smsIntent);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void callPhone(Context ctx, String phone, boolean isDirectCall){
        try {
            Intent phoneIntent = new Intent(isDirectCall ? Intent.ACTION_CALL : Intent.ACTION_DIAL,
                    Uri.parse("tel:" + phone));
            ctx.startActivity(phoneIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}