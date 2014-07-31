package com.ces.cloudnote.app.qiyi.more;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ces.cloudnote.app.R;

/**
 * 
 * @author iteye whyhappy01
 * 
 */
public class MoreActivity extends Activity {
	TextView mTitleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_activity);
		prepareView();
		mTitleView.setText(R.string.category_more);
	}

	private void prepareView() {
		mTitleView = (TextView) findViewById(R.id.title_text);
	}
}
