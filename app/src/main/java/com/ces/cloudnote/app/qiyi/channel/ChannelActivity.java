package com.ces.cloudnote.app.qiyi.channel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ces.cloudnote.app.R;

/**
 * 
 * @author iteye whyhappy01
 * 
 */
public class ChannelActivity extends Activity {
	TextView mTitleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qiyi_channel);
		prepareView();
		mTitleView.setText(R.string.category_channel);
	}

	private void prepareView() {
		mTitleView = (TextView) findViewById(R.id.title_text);
	}
}
