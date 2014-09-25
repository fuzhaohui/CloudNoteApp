package com.ces.cloudnote.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ReceiveActivity extends Activity {


    TextView tv1;
    Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receive);

//		Intent intent = getIntent();
//		String action = intent.getAction();
//		String type = intent.getType();
//
//		if(Intent.ACTION_SEND.equals(action)) {
//			if("text/plain".equals(type)) {
//				handleSendText(intent);
//			} else  if(type.startsWith("image/")) {
//				handleSendImage(intent);
//			}
//		} else if(Intent.ACTION_SEND_MULTIPLE.equals(action)) {
//			if(type.startsWith("image/")) {
//				handleSendMultipleImages(intent);
//			}
//		} else {
//
//		}

        // 定义UI组件
        Button b1 = (Button) findViewById(R.id.button1);
        tv1 = (TextView) findViewById(R.id.textView1);

        // 获取LocationManager对象
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // 定义Criteria对象
        Criteria criteria = new Criteria();
        // 设置定位精确度 Criteria.ACCURACY_COARSE 比较粗略， Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否需要海拔信息 Altitude
        criteria.setAltitudeRequired(true);
        // 设置是否需要方位信息 Bearing
        criteria.setBearingRequired(true);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(true);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        // 获取GPS信息提供者
        String bestProvider = lm.getBestProvider(criteria, true);
        Log.i("yao", "bestProvider = " + bestProvider);

        // 获取定位信息
        location = lm.getLastKnownLocation(bestProvider);
        if(location == null) {

        }

        // 给按钮绑定点击监听器
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLocation(location);
            }
        });

        // 位置监听器
        LocationListener locationListener = new LocationListener() {

            // 当位置改变时触发
            @Override
            public void onLocationChanged(Location location) {
                Log.i("yao", location.toString());
                updateLocation(location);
            }

            // Provider失效时触发
            @Override
            public void onProviderDisabled(String arg0) {
                Log.i("yao", arg0);

            }

            // Provider可用时触发
            @Override
            public void onProviderEnabled(String arg0) {
                Log.i("yao", arg0);
            }

            // Provider状态改变时触发
            @Override
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
                Log.i("yao", "onStatusChanged");
            }
        };

        // 500毫秒更新一次，忽略位置变化
        lm.requestLocationUpdates(bestProvider, 500, 0, locationListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.receive, menu);
		return true;
	}

	private void handleSendText(Intent intent) {
	    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	    if (sharedText != null) {
	        // Update UI to reflect text being shared
	    }
	}

	private void handleSendImage(Intent intent) {
	    Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
	    if (imageUri != null) {
	        // Update UI to reflect image being shared
	    }
	}

	private void handleSendMultipleImages(Intent intent) {
	    ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
	    if (imageUris != null) {
	        // Update UI to reflect multiple images being shared
	    }
	}

    // 更新位置信息
    private void updateLocation(Location location) {
        if (location != null) {
            tv1.setText("定位对象信息如下：" + location.toString() + "\n\t其中经度：" + location.getLongitude() + "\n\t其中纬度："
                    + location.getLatitude());
        } else {
            Log.i("yao", "没有获取到定位对象Location");
        }
    }

}
