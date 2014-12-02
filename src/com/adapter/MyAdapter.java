package com.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fanz.notebook2.R;

import android.content.Context;
import android.widget.SimpleAdapter;

public class MyAdapter {

	Context context;
	public MyAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	public SimpleAdapter getMenuAdapter(){
		String[] items={"�ҵ��б�","����","����","����","�����б�","������ҵ�����"};
		int[] images={
				R.drawable.ic_launcher,
				0,
				0,
				0,
				R.drawable.ic_launcher,
				R.drawable.ic_launcher};
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(int i=0;i<6;i++){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("itemName", items[i]);
			map.put("itemImage", images[i]);
			list.add(map);
		}
		SimpleAdapter adapter=new SimpleAdapter(context, list, R.layout.menu_adapter_item,
				new String[]{"itemImage","itemName"}, 
				new int[]{R.id.menu_item_image,R.id.menu_item_text});
		
		return adapter;
	}

	public SimpleAdapter getConfigurationAdapter() {
		// TODO Auto-generated method stub
		String[] items={"�˻���Ϣ","ͬ��","������","�汾��Ϣ"};
		int[] images={
				R.drawable.ic_launcher,
				R.drawable.ic_launcher,
				R.drawable.ic_launcher,
				R.drawable.ic_launcher};
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(int i=0;i<4;i++){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("itemName", items[i]);
			map.put("itemImage", images[i]);
			list.add(map);
		}
		SimpleAdapter adapter=new SimpleAdapter(context, list, R.layout.menu_adapter_item,
				new String[]{"itemImage","itemName"}, 
				new int[]{R.id.menu_item_image,R.id.menu_item_text});
		
		return adapter;
	}
}
