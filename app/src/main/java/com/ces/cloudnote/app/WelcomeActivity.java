package com.ces.cloudnote.app;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ces.cloudnote.app.beans.Person;
import com.ces.cloudnote.app.layout.MyScrollLayout;
import com.ces.cloudnote.app.utils.OnViewChangeListener;

public class WelcomeActivity extends Activity implements OnViewChangeListener {

	private MyScrollLayout mScrollLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	private Button startBtn;
	private RelativeLayout mainRLayout;
	private LinearLayout pointLLayout;
	private LinearLayout leftLayout;
	private LinearLayout rightLayout;
	private LinearLayout animLayout;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        initView();
        //SQLiteDatabase database = new SQLiteDatabase();
        SQLiteDatabase db = openOrCreateDatabase("test.db", Context.MODE_PRIVATE, null);
        db.execSQL("DROP TABLE IF EXISTS person");
        //创建person表
        db.execSQL("CREATE TABLE person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");
        Person person = new Person();
        person.name = "john";
        person.age = 30;
        //插入数据
        db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{person.name, person.age});

        person.name = "david";
        person.age = 33;
        //ContentValues以键值对的形式存放数据
        ContentValues cv = new ContentValues();
        cv.put("name", person.name);
        cv.put("age", person.age);
        //插入ContentValues中的数据
        db.insert("person", null, cv);

        cv = new ContentValues();
        cv.put("age", 35);
        //更新数据
        db.update("person", cv, "name = ?", new String[]{"john"});

        Cursor c = db.rawQuery("SELECT * FROM person WHERE age >= ?", new String[]{"33"});
        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            int age = c.getInt(c.getColumnIndex("age"));
            Log.i("db", "_id=>" + _id + ", name=>" + name + ", age=>" + age);
        }
        c.close();

        //删除数据
        db.delete("person", "age < ?", new String[]{"35"});

        //关闭当前数据库
        db.close();

        if(true) {
            Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
            WelcomeActivity.this.startActivity(intent);
            WelcomeActivity.this.finish();
            overridePendingTransition(R.anim.zoom_out_enter, R.anim.zoom_out_exit);
        }
    }
    
	private void initView() {
		mScrollLayout  = (MyScrollLayout) findViewById(R.id.ScrollLayout);
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);
		mainRLayout = (RelativeLayout) findViewById(R.id.mainRLayout);
		startBtn = (Button) findViewById(R.id.startBtn);
		startBtn.setOnClickListener(onClick);
		animLayout = (LinearLayout) findViewById(R.id.animLayout);
		leftLayout  = (LinearLayout) findViewById(R.id.leftLayout);
		rightLayout  = (LinearLayout) findViewById(R.id.rightLayout);
		count = mScrollLayout.getChildCount();
		imgs = new ImageView[count];
		for(int i = 0; i< count;i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;
		imgs[currentItem].setEnabled(false);
		mScrollLayout.SetOnViewChangeListener(this);
	}
	
	private View.OnClickListener onClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.startBtn:
				mScrollLayout.setVisibility(View.GONE);
				pointLLayout.setVisibility(View.GONE);
				animLayout.setVisibility(View.VISIBLE);
				mainRLayout.setBackgroundResource(R.drawable.whatsnew_bg);
				Animation leftOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_left);
				Animation rightOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_right);
//				Animation leftOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadedout_to_left_down);
//				Animation rightOutAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadedout_to_right_down);
				leftLayout.setAnimation(leftOutAnimation);
				rightLayout.setAnimation(rightOutAnimation);
				leftOutAnimation.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {
						mainRLayout.setBackgroundColor(getResources().getColor(R.color.bgColor));
					}
					@Override
					public void onAnimationRepeat(Animation animation) {
					}
					@Override
					public void onAnimationEnd(Animation animation) {
						leftLayout.setVisibility(View.GONE);
						rightLayout.setVisibility(View.GONE);
						Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
						WelcomeActivity.this.startActivity(intent);
						WelcomeActivity.this.finish();
						overridePendingTransition(R.anim.zoom_out_enter, R.anim.zoom_out_exit);
					}
				});
				break;
			}
		}
	};

	@Override
	public void OnViewChange(int position) {
		setCurrentPoint(position);
	}

	private void setCurrentPoint(int position) {
		if(position < 0 || position > count -1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}

}
