package com.ces.cloudnote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by fuzhaohui on 14/10/29.
 */
public class AMapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animator);

        new CountDownTimer(2000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                Intent intent = new Intent();
                intent.setClass(AMapActivity.this, MainActivity.class);
                startActivity(intent);

                int VERSION=Integer.parseInt(android.os.Build.VERSION.SDK);
                if(VERSION >= 5){
                    AMapActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                }
                finish();
            }
        }.start();
    }
}
