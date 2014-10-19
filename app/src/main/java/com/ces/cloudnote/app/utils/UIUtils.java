package com.ces.cloudnote.app.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UIUtils {

    private static Toast mToast;

    public static void showToast(Context ctx, String content, int duration) {
        if (mToast != null) {
            mToast.setDuration(duration);
            mToast.setText(content);
        } else {
            mToast = Toast.makeText(ctx, content, duration);
        }
        mToast.show();
    }

    public static void show(View view, boolean isShow){
        if(isShow) {
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 默认是显示长时间
     *
     * @param ctx
     * @param content
     */
    public static void showToast(Context ctx, String content) {
        showToast(ctx, content, Toast.LENGTH_LONG);
    }

    public static void showToast(Context ctx, int resId) {
        showToast(ctx, ctx.getResources().getString(resId));
    }

    public static void showToast(Context ctx, int resId, int duration) {
        showToast(ctx, ctx.getResources().getString(resId), duration);
    }

    public static void showToastLong(Context ctx, String content) {
        showToast(ctx, content, Toast.LENGTH_LONG);
    }

    public static void showToastLong(Context ctx, int resId) {
        showToast(ctx, ctx.getText(resId).toString());
    }

    public static void showToastShort(Context ctx, String content) {
        showToast(ctx, content, Toast.LENGTH_SHORT);
    }

    public static void showToastShort(Context ctx, int resId) {
        showToast(ctx, ctx.getText(resId).toString(), Toast.LENGTH_SHORT);
    }


    public static boolean isEmpty(TextView... views){
        for(TextView tv : views){
            if(TextUtils.isEmpty(tv.getText().toString().trim()))
                return true;
        }
        return false;
    }


    public static String getRealText(EditText et){
        if(et != null)
            return et.getText().toString().trim();
        return "";
    }


    /**
     * 显示不定时进度条
     *
     * @param con
     * @param resId
     * @return
     */
    public static ProgressDialog createLoadingDialog(Context con, int resId) {
        return createLoadingDialog(con, con.getText(resId).toString());
    }

    public static ProgressDialog createLoadingDialog(Context con, int resId, OnCancelListener listener) {
        return createLoadingDialog(con, con.getText(resId).toString(), listener);
    }

    /**
     * 显示不定时进度条
     *
     * @param con
     * @param content
     * @return
     */
    public static ProgressDialog createLoadingDialog(Context con, String content) {
        return createLoadingDialog(con,content,null);
    }

    public static ProgressDialog createLoadingDialog(Context con, String content, OnCancelListener listener) {
        ProgressDialog mProgressDialog = new ProgressDialog(con);
        mProgressDialog.setMessage(content);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        if(listener != null)
            mProgressDialog.setOnCancelListener(listener);
        return mProgressDialog;
    }

    /**
     * 可取消的不定时进度条
     *
     * @param con
     * @param resId
     * @param listener
     * @return
     */
    public static ProgressDialog createCancelableLoadingDialog(Context con, int resId,
                                                               OnCancelListener listener) {
        ProgressDialog mProgressDialog = createLoadingDialog(con, resId);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(listener);
        return mProgressDialog;
    }

    /**
     * 可取消的不定时进度条
     *
     * @param con
     * @param content
     * @param listener
     * @return
     */
    public static ProgressDialog createCancelableLoadingDialog(Context con, String content,
                                                               OnCancelListener listener) {
        ProgressDialog mProgressDialog = createLoadingDialog(con, content);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setOnCancelListener(listener);
        return mProgressDialog;
    }

    /**
     * 创建进度条对话框
     *
     * @param con
     * @param title
     * @param max
     * @return
     */
    public static ProgressDialog createProgressDialog(Context con, String title, int max) {
        ProgressDialog mProgressDialog = new ProgressDialog(con);
        mProgressDialog.setTitle(title);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setMax(max);
        mProgressDialog.setProgress(0);
        return mProgressDialog;
    }

    /**
     * 创建提示对话框
     *
     * @param ctx
     * @param title
     * @param message
     * @return
     */
    public static AlertDialog createAlertDialog(
            Context ctx, String title, String message, String buttonText) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx).setTitle(title).setMessage(message)
                .setNeutralButton(buttonText, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        return alertDialog;
    }

    public static AlertDialog createAlertDialog(
            Context ctx, String title, String message, String buttonText,
            OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx).setTitle(title).setMessage(message)
                .setNeutralButton(buttonText, listener).create();
        return alertDialog;
    }

    /**
     * 创建提示对话框
     *
     * @param ctx
     * @param titleId
     * @param messageId
     * @return
     */
    public static AlertDialog createAlertDialog(
            Context ctx, int titleId, int messageId, int buttonTextId) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx).setTitle(ctx.getText(titleId))
                .setMessage(ctx.getText(messageId))
                .setNeutralButton(buttonTextId, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        return alertDialog;
    }

    public static AlertDialog createAlertDialog(
            Context ctx, int titleId, int messageId,
            int buttonTextId, OnClickListener listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx).setTitle(ctx.getText(titleId))
                .setMessage(ctx.getText(messageId))
                .setNeutralButton(buttonTextId, listener).create();
        alertDialog.setCancelable(false);
        return alertDialog;
    }

    /**
     * 创建确认对话框
     *
     * @param ctx
     * @param title
     * @param message
     * @param confirmButtonString    确认按钮显示的内容
     * @param confirmOnClickListener 点击确认按钮执行的操作
     * @return
     */
    public static AlertDialog createAlertDialog(
            Context ctx, String title, String message,
            String confirmButtonString, String cancelButtonString,
            OnClickListener confirmOnClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                        confirmButtonString,
                        confirmOnClickListener)
                .setNegativeButton(cancelButtonString,
                        new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create();
        return alertDialog;
    }

    public static AlertDialog createAlertDialog(
            Context ctx, String title, String message,
            String confirmButtonString,
            OnClickListener confirmOnClickListener,
            String cancelButtonString,
            OnClickListener onCancelButtonClick) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(
                        confirmButtonString,
                        confirmOnClickListener)
                .setNegativeButton(cancelButtonString,onCancelButtonClick).create();
        return alertDialog;
    }

    public interface OnDialogCallBack {
        public void onStringBack(String result);
    }

    public static AlertDialog createAlertDialog(
            Context ctx, String title, String positiveButton,
            String negativeButton, final EditText et,
            final OnDialogCallBack callBack) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setView(et)
                .setPositiveButton(positiveButton, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callBack.onStringBack(et.getText().toString());
                    }
                })
                .setNegativeButton(negativeButton, null).create();
        return alertDialog;
    }

    public static AlertDialog createSingleChoiceAlertDialog(
            final Context ctx, String title, String positiveButton,
            String negativeButton, final int arrayId,
            int checkedItem, final OnDialogCallBack callBack) {
        final String[] result = new String[1];
        String[] perpare = ctx.getResources().getStringArray(arrayId);
        if(perpare != null && perpare.length > 0) {
            result[0] = ctx.getResources().getStringArray(arrayId)[0];
        }
        AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setSingleChoiceItems(arrayId, checkedItem, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String[] str = ctx.getResources().getStringArray(arrayId);
                        if (i >= 0 && str != null && str.length > i) {
                            result[0] = str[i];
                        }
                    }
                })
                .setPositiveButton(positiveButton, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        callBack.onStringBack(result[0]);
                    }
                })
                .setNegativeButton(negativeButton, null).create();
        return alertDialog;
    }

    public static AlertDialog createListAlertDialog(
            final Context ctx, String title,
            String[] items, final OnClickListener callBack) {
        AlertDialog alertDialog = new AlertDialog.Builder(ctx)
                .setTitle(title)
                .setItems(items, callBack)
                .create();
        return alertDialog;
    }


//    public static AlertDialog createAlertDialog(Context ctx, String title, String message,
//            String confirmButtonString, DialogInterface.OnClickListener confirmOnClickListener,
//            String cancelButtonString, DialogInterface.OnClickListener cancelOnClickListener) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
//        if (!TextUtils.isEmpty(title)) {
//            builder.setTitle(title);
//        }
//        if (!TextUtils.isEmpty(message)) {
//            builder.setTitle(message);
//        }
//        if (!TextUtils.isEmpty(confirmButtonString)) {
//            builder.setPositiveButton(confirmButtonString, confirmOnClickListener);
//        } else {
//            builder.setPositiveButton(R.string.str_confirm, confirmOnClickListener);
//        }
//        if (!TextUtils.isEmpty(cancelButtonString)) {
//            builder.setNegativeButton(cancelButtonString, cancelOnClickListener);
//        } else {
//            builder.setNegativeButton(R.string.str_cancel, cancelOnClickListener);
//        }
//        return builder.create();
//    }


    public  static <T> void startActivity(Context ctx, Class<T> target) {
        startActivity(ctx, target, null);
    }

    public  static <T> void startActivity(Context ctx, Class<T> target, Bundle bundle) {
        Intent intent = new Intent(ctx, target);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        ctx.startActivity(intent);
    }

    /**
     * 设置Activity无标题栏
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void showNoTitleHoneycomb(Activity activity) {
        // 设置窗口属性
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    /**
     * 设置Activity无标题栏
     *
     * @param activity
     */
    public static void showNoTitle(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            showNoTitleHoneycomb(activity);
        }
    }

    /**
     * get Screen Width
     *
     * @param a
     * @return
     */
    public static int getScreenWidth(Activity a) {
        DisplayMetrics metric = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        return width;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断是否是平板
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    // ------Layout-------//
    public static final int LPW = LayoutParams.WRAP_CONTENT;
    public static final int LPF = LayoutParams.MATCH_PARENT;

    /**
     * 获取宽高均为WRAP_CONTENT的布局
     *
     * @return
     */
    public static LayoutParams getWWLp() {
        return getLp(LPW, LPW);
    }

    public static LayoutParams getFWLp() {
        return getLp(LPF, LPW);
    }

    public static LayoutParams getLp(int width, int height) {
        LayoutParams lp = new LayoutParams(width, height);
        return lp;
    }

    public static LinearLayout.LayoutParams getLllp(int width, int height) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
        return lp;
    }

    public static RelativeLayout.LayoutParams getRllp(int width, int height) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
        return lp;
    }

    public static FrameLayout.LayoutParams getFllp(int width, int height) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
        return lp;
    }
}
