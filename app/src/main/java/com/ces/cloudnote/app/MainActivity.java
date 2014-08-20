package com.ces.cloudnote.app;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.EditText;

import com.ces.cloudnote.app.bitmapfun.ui.ImageGridActivity;
import com.ces.cloudnote.app.contactslist.ContactsListActivity;
import com.ces.cloudnote.app.fragments.FragmentMainActivity;
import com.ces.cloudnote.app.imageloader.HomeActivity;
import com.ces.cloudnote.app.navigation.NavigateActivity;
import com.ces.cloudnote.app.navigationdrawer.NavigateDrawerActivity;
import com.ces.cloudnote.app.networkusage.NetworkActivity;
import com.ces.cloudnote.app.newsreader.NewsReaderActivity;
import com.ces.cloudnote.app.photobyintent.PhotoIntentActivity;
import com.ces.cloudnote.app.qiyi.QiyiMainActivity;
import com.ces.cloudnote.app.voicemail.AddVoicemailActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends Activity implements OnClickListener {

    public static final String EXTRA_MESSAGE = "com.ces.cloudnote.app.MESSAGE";
    public static final int PICK_CONTACT_REQUEST = 1;



	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button sendMessageBtn = (Button)findViewById(R.id.send_message);
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
        Button imageLoaderBtn = (Button)findViewById(R.id.imageLoader_btn);
        imageLoaderBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                imageloader(view);
            }
        });
        Button naviageMenuBtn = (Button)findViewById(R.id.navigateMenu_btn);
        naviageMenuBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                navigate(view);
            }
        });
        Button photoIntentBtn = (Button)findViewById(R.id.photoIntent_btn);
        photoIntentBtn.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                photointent(view);
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
        Button sendMessage = (Button)findViewById(R.id.send_message);
        sendMessage.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View view) {
                sendMessage(view);
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
        Button photoProcessingBtn = (Button)findViewById(R.id.photoprocessing);
        photoProcessingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
    	
    	
    	
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        
        startActivity(intent);
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
    
    public void imageloader(View view) {
    	Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    
    public void navigate(View view) {
    	Intent intent = new Intent(this, NavigateActivity.class);
        startActivity(intent);
    }
    
    public void photointent(View view) {
    	Intent intent = new Intent(this, PhotoIntentActivity.class);
    	startActivity(intent);
    }

    public void drawer(View view) {
    	Intent intent = new Intent(this, NavigateDrawerActivity.class);
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

	@Override
	public void onClick(View v) {
		Log.v("button id: ", v.getId() + "");
	}
}
