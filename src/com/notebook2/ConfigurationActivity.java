package com.notebook2;

import com.adapter.MyAdapter;
import com.fanz.notebook2.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ConfigurationActivity extends Activity{

	MyAdapter myAdapter;
	ListView listView;
	public ConfigurationActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.configuration_activity);
		myAdapter=new MyAdapter(this);
		listView=(ListView)findViewById(R.id.configuration_list);
		SimpleAdapter adapter=myAdapter.getConfigurationAdapter();
	
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}});
	}
}
