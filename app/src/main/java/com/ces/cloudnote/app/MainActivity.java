package com.ces.cloudnote.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ces.cloudnote.app.components.RoundImageView;
import com.ces.cloudnote.app.contactslist.ContactsListActivity;
import com.ces.cloudnote.app.contactslist.ContactsListFragment;
import com.ces.cloudnote.app.fragments.BaseFragment;
import com.ces.cloudnote.app.fragments.GridFragment;
import com.ces.cloudnote.app.fragments.ListFragment;
import com.ces.cloudnote.app.layout.DragLayout;
import com.ces.cloudnote.app.qiyi.QiyiMainActivity;
import com.ces.cloudnote.app.utils.Util;
import com.nineoldandroids.view.ViewHelper;

public class MainActivity extends FragmentActivity implements BaseFragment.OnFragmentInteractionListener {

    private DragLayout dl;
    private ListView lv;
    private RoundImageView iv_main_avatar, iv_menu_avatar;
    private TextView tv_title, tv_realname;
    private BaseFragment baseFragment;
    private String[] menuItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        menuItems = new String[] { "列表",
                "系统相册",
                getText(R.string.activity_contacts_list).toString(),
                getText(R.string.qiyi).toString(),
                getText(R.string.)};

        initDragLayout();
        initView();
    }

    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
                //lv.smoothScrollToPosition(new Random().nextInt(30));
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
        ViewHelper.setAlpha(iv_main_avatar, 1 - percent);

        int color = (Integer) Util.evaluate(percent,
                Color.parseColor("#ff000000"), Color.parseColor("#00000000"));
        dl.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_OVER);
    }

    private void initView() {
        iv_main_avatar = (RoundImageView) findViewById(R.id.iv_main_avatar);
        iv_menu_avatar = (RoundImageView) findViewById(R.id.iv_menu_avatar);

        iv_main_avatar.setImageResource(R.drawable.test3);
        iv_menu_avatar.setImageResource(R.drawable.test2);

        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_realname = (TextView) findViewById(R.id.tv_realname);

        tv_realname.setText("张三GG");

        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                R.layout.item_text, menuItems
        ));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                switch (position) {
                    case 0 :
                        baseFragment = new ListFragment();
                        changeFragment(baseFragment, position);
                        break;
                    case 1 :
                        baseFragment = new GridFragment();
                        changeFragment(baseFragment, position);
                        break;
                    case 2 :
                        startActivity(ContactsListActivity.class);
                        break;
                    case 3 :
                        startActivity(QiyiMainActivity.class);
                        break;
                }

                dl.close();
            }
        });
        iv_main_avatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dl.open();
            }
        });

        if(baseFragment == null) {
            baseFragment = new ListFragment();
        }
        changeFragment(baseFragment, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    @Override
    public void onFragmentInteraction(String id) {

    }

    private void changeFragment(Fragment targetFragment, int position) {
        tv_title.setText(menuItems[position]);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                        //.add(R.id.main_fragment, new ListFragment())
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private <T> void startActivity(Class<T> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
