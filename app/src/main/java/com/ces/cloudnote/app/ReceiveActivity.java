package com.ces.cloudnote.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;

import java.util.ArrayList;

public class ReceiveActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receive);

		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		if(Intent.ACTION_SEND.equals(action)) {
			if("text/plain".equals(type)) {
				handleSendText(intent);
			} else  if(type.startsWith("image/")) {
				handleSendImage(intent);
			}
		} else if(Intent.ACTION_SEND_MULTIPLE.equals(action)) {
			if(type.startsWith("image/")) {
				handleSendMultipleImages(intent);
			}
		} else {

		}
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

}
