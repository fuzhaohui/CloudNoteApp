package com.ces.cloudnote.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ces.cloudnote.app.adapter.ImageAdapter;
import com.ces.cloudnote.app.baidumap.DemoMainActivity;
import com.ces.cloudnote.app.bitmapfun.ui.ImageGridActivity;
import com.ces.cloudnote.app.contactslist.ContactsListActivity;
import com.ces.cloudnote.app.drawerMenu.DrawerActivity;
import com.ces.cloudnote.app.fragments.FragmentMainActivity;
import com.ces.cloudnote.app.layout.DragLayout;
import com.ces.cloudnote.app.navigationMenu.NavigateActivity;
import com.ces.cloudnote.app.networkusage.NetworkActivity;
import com.ces.cloudnote.app.newsreader.NewsReaderActivity;
import com.ces.cloudnote.app.photo.PhotoProcessingActivity;
import com.ces.cloudnote.app.photobyintent.PhotoIntentActivity;
import com.ces.cloudnote.app.qiyi.QiyiMainActivity;
import com.ces.cloudnote.app.resideMenu.MenuActivity;
import com.ces.cloudnote.app.utils.Util;
import com.ces.cloudnote.app.voiceemail.AddVoicemailActivity;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class MainActivity extends Activity {

    private DragLayout dl;
    private GridView gv_img;
    private ImageAdapter adapter;
    private ListView lv;
    private TextView tv_noimg;
    private ImageView iv_icon, iv_bottom;

    public static final String EXTRA_MESSAGE = "com.ces.cloudnote.app.MESSAGE";
    public static final int PICK_CONTACT_REQUEST = 1;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Util.initImageLoader(this);
        initDragLayout();
        initView();


       /* Button sendMessageBtn = (Button)findViewById(R.id.send_message_btn);
        sendMessageBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
                sendMessage(view);
            }
        });
        Button contactBtn = (Button)findViewById(R.id.contact_btn);
        contactBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                contact(view);
            }
        });
        Button uiHiderBtn = (Button)findViewById(R.id.uiHider_btn);
        uiHiderBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                uihider(view);
            }
        });
        Button navigateMenuBtn = (Button)findViewById(R.id.navigateMenu_btn);
        navigateMenuBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                navigateMenu(view);
            }
        });
        Button photoIntentBtn = (Button)findViewById(R.id.photoIntent_btn);
        photoIntentBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                photoIntent(view);
            }
        });
        Button drawerMenuBtn = (Button)findViewById(R.id.drawerMenu_btn);
        drawerMenuBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                drawer(view);
            }
        });
        Button newReaderBtn = (Button)findViewById(R.id.newsReader_btn);
        newReaderBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                newsreader(view);
            }
        });
        Button voiceEmailBtn = (Button)findViewById(R.id.voiceEmail_btn);
        voiceEmailBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                voicemail(view);
            }
        });
        Button networkUsageBtn = (Button)findViewById(R.id.networkUsage_btn);
        networkUsageBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                networkusage(view);
            }
        });
        Button fragmentsBtn = (Button)findViewById(R.id.fragments_btn);
        fragmentsBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                fragments(view);
            }
        });
        Button qiyiBtn = (Button)findViewById(R.id.qiyi_btn);
        qiyiBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                qiyi(view);
            }
        });
        Button bitmapFunBtn = (Button)findViewById(R.id.bitmapFun_btn);
        bitmapFunBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                bitmapfun(view);
            }
        });
        Button baiduMapBtn = (Button)findViewById(R.id.baiduMap_btn);
        baiduMapBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                baiduMap(view);
            }
        });
        Button photoProcessingBtn = (Button)findViewById(R.id.photoProcessing_btn);
        photoProcessingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                photoProcessing(view);
            }
        });
        Button webViewBtn = (Button)findViewById(R.id.webView_btn);
        webViewBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                webView(view);
            }
        });
        Button demoLayoutBtn = (Button)findViewById(R.id.demoLayout);
        demoLayoutBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                demoLayout(view);
            }
        });
        Button resideMenuBtn = (Button)findViewById(R.id.resideMenu_btn);
        resideMenuBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu(view);
            }
        });

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // For the main activity, make sure the app icon in the action bar
            // does not behave as a button
            //getSupportActionBar().setHomeButtonEnabled(false);
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
    	
    	// 分享存储
    	Context context = getApplicationContext();
    	SharedPreferences preferences = context.getSharedPreferences(getString(R.string.share_key), Context.MODE_PRIVATE);
    	SharedPreferences.Editor edit = preferences.edit();
    	edit.putString("name", "fuzhaohui");
    	edit.commit();

    	
    	String defaultName = getResources().getString(R.string.default_name);
    	String myName = preferences.getString(getString(R.string.share_key), defaultName);
    	System.out.println(myName);
    	
    	String fileName = getResources().getString(R.string.show_message);
    	File file = new File(context.getFilesDir(), fileName);
    	System.out.println(file.getAbsolutePath());
    	
    	// 向手机上传文件
    	String string = "Hello world!";
    	FileOutputStream outputStream;
    	
    	try {
			outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
			outputStream.write(string.getBytes());
			outputStream.close();
    	} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	// 或者使用方式 
    	
    	
    	
    	//Intent intent = new Intent(this, DisplayMessageActivity.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        
        //startActivity(intent);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();
        String tel = tm.getLine1Number();//手机号码
        String imei = tm.getSimSerialNumber();
        String imsi = tm.getSubscriberId();
        Log.i("deviceId", deviceid);
        Log.i("Tel", tel);
        Log.i("IMEI", imei);
        Log.i("IMSI", imsi);
    }
    
    public void contact(View view) {
    	Intent intent = new Intent(this, ContactsListActivity.class);
        startActivity(intent);
    }
    
    public void uihider(View view) {
    	Intent intent = new Intent(this, SystemUIHidderActivity.class);
        startActivity(intent);
    }
    
    public void voicemail(View view) {
    	Intent intent = new Intent(this, AddVoicemailActivity.class);
        startActivity(intent);
    }

    public void navigateMenu(View view) {
    	Intent intent = new Intent(this, NavigateActivity.class);
        startActivity(intent);
    }
    
    public void photoIntent(View view) {
    	Intent intent = new Intent(this, PhotoIntentActivity.class);
    	startActivity(intent);
    }

    public void drawer(View view) {
    	Intent intent = new Intent(this, DrawerActivity.class);
    	startActivity(intent);
    }
    
    public void newsreader(View view) {
    	Intent intent = new Intent(this, NewsReaderActivity.class);
    	startActivity(intent);
    }
    
    public void networkusage(View view) {
    	Intent intent = new Intent(this, NetworkActivity.class);
    	startActivity(intent);
    }

    public void fragments(View view) {
    	Intent intent = new Intent(this, FragmentMainActivity.class);
    	startActivity(intent);
    }
    
    public void qiyi(View view) {
    	Intent intent = new Intent(this, QiyiMainActivity.class);
    	startActivity(intent);
    }
    
    public void bitmapfun(View view) {
    	Intent intent = new Intent(this, ImageGridActivity.class);
    	startActivity(intent);
    }

    public void baiduMap(View view) {
        Intent intent = new Intent(this, DemoMainActivity.class);
        startActivity(intent);
    }

    public void webView(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    public void demoLayout(View view) {
        Intent intent = new Intent(this, DemoLayoutActivity.class);
        startActivity(intent);
    }

    public void resideMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void photoProcessing(View view) {
        Intent intent = new Intent(this, PhotoProcessingActivity.class);
        startActivity(intent);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request it is that we're responding to
	    if (requestCode == PICK_CONTACT_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	            // Get the URI that points to the selected contact
	            Uri contactUri = data.getData();
	            // We only need the NUMBER column, because there will be only one row in the result
	            String[] projection = {Phone.NUMBER};

	            // Perform the query on the contact to get the NUMBER column
	            // We don't need a selection or sort order (there's only one result for the given URI)
	            // CAUTION: The query() method should be called from a separate thread to avoid blocking
	            // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
	            // Consider using CursorLoader to perform the query.
	            Cursor cursor = getContentResolver()
	                    .query(contactUri, projection, null, null, null);
	            cursor.moveToFirst();

	            // Retrieve the phone number from the NUMBER column
	            int column = cursor.getColumnIndex(Phone.NUMBER);
	            String number = cursor.getString(column);
	            System.out.println(number);
	            
	            // Do something with the phone number...
	        }
	    }
	}

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
                lv.smoothScrollToPosition(new Random().nextInt(30));
            }

            @Override
            public void onClose() {
            }

            @Override
            public void onDrag(float percent) {
                animate(percent);
            }
        });
    }

    private void animate(float percent) {
        ViewGroup vg_left = dl.getVg_left();
        ViewGroup vg_main = dl.getVg_main();

        float f1 = 1 - percent * 0.3f;
        ViewHelper.setScaleX(vg_main, f1);
        ViewHelper.setScaleY(vg_main, f1);
        ViewHelper.setTranslationX(vg_left, -vg_left.getWidth() / 2.2f
                + vg_left.getWidth() / 2.2f * percent);
        ViewHelper.setScaleX(vg_left, 0.5f + 0.5f * percent);
        ViewHelper.setScaleY(vg_left, 0.5f + 0.5f * percent);
        ViewHelper.setAlpha(vg_left, percent);
        ViewHelper.setAlpha(iv_icon, 1 - percent);

        int color = (Integer) Util.evaluate(percent, Color.parseColor("#ff000000"), Color.parseColor("#00000000"));
        dl.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_OVER);
    }

    private void initView() {
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
        gv_img = (GridView) findViewById(R.id.gv_img);
        tv_noimg = (TextView) findViewById(R.id.iv_noimg);
        gv_img.setFastScrollEnabled(true);
        adapter = new ImageAdapter(this);
        gv_img.setAdapter(adapter);
        gv_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                intent.putExtra("path", adapter.getItem(position));
                startActivity(intent);
            }
        });
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.item_text, new String[] { "NewBee", "ViCi Gaming",
                "Evil Geniuses", "Team DK", "Invictus Gaming", "LGD",
                "Natus Vincere", "Team Empire", "Alliance", "Cloud9",
                "Titan", "Mousesports", "Fnatic", "Team Liquid",
                "MVP Phoenix", "NewBee", "ViCi Gaming",
                "Evil Geniuses", "Team DK", "Invictus Gaming", "LGD",
                "Natus Vincere", "Team Empire", "Alliance", "Cloud9",
                "Titan", "Mousesports", "Fnatic", "Team Liquid",
                "MVP Phoenix" }));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                Toast.makeText(getApplicationContext(), "click " + position,
                        Toast.LENGTH_LONG).show();
            }
        });
        iv_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImage();
    }

    private void loadImage() {
        adapter.addAll(Util.getGalleryPhotos(this));
        if (adapter.isEmpty()) {
            tv_noimg.setVisibility(View.VISIBLE);
        } else {
            tv_noimg.setVisibility(View.GONE);
            String s = "file://" + adapter.getItem(0);
            ImageLoader.getInstance().displayImage(s, iv_icon);
            ImageLoader.getInstance().displayImage(s, iv_bottom);
        }
        iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }


}
