package com.ces.cloudnote.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class DemoActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.demo);

        // 获得AutoCompleteTextView组件
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
        // 从strings.xml获得国家名称的数组
        String[] countries = getResources().getStringArray(R.array.countries_array);
        // 创建适配器，并用setAdapter（）为AutoCompleteTextView设置此适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);

        Spinner  spinner = (Spinner)findViewById(R.id.planets_spinner);
        // 使用默认的下拉列表布局和数组plants_array来创建数组适配器ArrayAdapter
        ArrayAdapter<CharSequence> adapter1 =ArrayAdapter.createFromResource(this,R.array.planets_array, android.R.layout.simple_spinner_item);
        // 指定所弹出的下拉列表的布局方式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // 为spinner设置适配器
        spinner.setAdapter(adapter1);


        Intent intent = new Intent();
        intent.setAction("MyBroadcast");
        intent.putExtra("value", 1000);
        sendBroadcast(intent);

	}



}
