package com.ces.cloudnote.app.location;

import android.app.AlertDialog;
import android.content.Context;  
import android.os.AsyncTask;  
import android.util.Log;  
import android.widget.TextView;  
 
public class GetLocation {  

    private AlertDialog dialog;
    private TextView textView;
    Context context;  
 
    public GetLocation(AlertDialog dialog, TextView textView, Context context) {  
        this.dialog = dialog;  
        this.textView = textView;  
        this.context = context;  
 
    }  
 
    public void DisplayLocation() {  
        new AsyncTask<Void, Void, String>() {  
 
            @Override 
            protected String doInBackground(Void... params) {  
                LMLocation location = null;  
                try {  
                    location = new GPSLocation().getGPSInfo(context);  
                } catch (Exception e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
                if (location == null)  
                    return null;  
                Log.d("debug", location.toString());  
                return location.toString();  
 
            }  
 
            @Override 
            protected void onPreExecute() {  
                dialog.show();  
                super.onPreExecute();  
            }  
 
            @Override 
            protected void onPostExecute(String result) {  
                if (result == null) {  
                    textView.setText("定位失败了...");  
                } else {  
                    textView.setText(result);  
                }  
                dialog.dismiss();  
                super.onPostExecute(result);  
            }  
 
        }.execute();  
    }  
 
} 