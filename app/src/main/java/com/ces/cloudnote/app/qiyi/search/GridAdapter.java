package com.ces.cloudnote.app.qiyi.search;

/**
 * 
 * @author iteye whyhappy01
 *
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ces.cloudnote.app.R;

public class GridAdapter extends BaseAdapter {
	Context mContext;
	String[] test1 = new String[] { "康熙来了", "东成西就", "西游记", "怪侠欧阳德", "如意",
			"美人心计", "倒霉熊", "宫锁珠帘", "喜洋洋", "北京爱情故事" };

	public GridAdapter(Context cnt) {
		this.mContext = cnt;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return test1.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (arg1 == null) {
			arg1 = LayoutInflater.from(mContext).inflate(
					R.layout.qiyi_search_grid_item, null);
		}
		TextView tv = (TextView) arg1.findViewById(R.id.title);
		tv.setText(test1[arg0]);
		return arg1;
	}

}
