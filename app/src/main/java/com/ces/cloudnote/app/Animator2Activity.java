package com.ces.cloudnote.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static android.view.animation.Animation.AnimationListener;

/**
 * Created by fuzhaohui on 14/10/29.
 */
public class Animator2Activity extends Activity implements AnimationListener {

    private ImageView imageView = null;
    private Animation alphaAnimation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animator2);

        imageView = (ImageView)findViewById(R.id.welcome_image_view);
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
        alphaAnimation.setFillEnabled(true); //启动Fill保持
        alphaAnimation.setFillAfter(true);  //设置动画的最后一帧是保持在View上面
        imageView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(this);  //为动画设置监听
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //动画结束时结束欢迎界面并转到软件的主界面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //在欢迎界面屏蔽BACK键
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }
}
