package com.adapter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fanz.notebook2.R;
import com.utils.MySQLiteHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleAdapter;

public class MyAdapter {

	Context context;
	public MyAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	public SimpleAdapter getMenuAdapter(){
		String[] items={"我的列表","生活","工作","其他","加星列表","分享给我的内容"};
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
		String[] items={"账户信息","同步","检查更新","版本信息"};
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

	public SimpleAdapter getMyListAdapter(){
		// TODO Auto-generated method stub
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		
		MySQLiteHelper helper=new MySQLiteHelper(context, "user_notebook", null, 4);
		SQLiteDatabase db= helper.getReadableDatabase();
		String sql="select * from user_notebook where name=?";
		String name=context.getSharedPreferences("localSave", context.MODE_WORLD_READABLE ).getString("name", "");
		Cursor cursor= db.rawQuery(sql, new String[]{name});
		int i=1;
		cursor.moveToLast();
		while(cursor.moveToPrevious()){
			cursor.moveToNext();
			String nam=cursor.getString(2);
	    	String title=cursor.getString(3);
	    	String content=cursor.getString(4);
	    	String date=cursor.getString(5);
	    	if(content.length()>30){
	    		content=content.substring(0, 30);
	    	}
	    	Map<String,String> map=new HashMap<String,String>();
			map.put("title", title);
	    	map.put("date", "by "+nam+' '+date);
	    	//summary显示前30个字符
	    	map.put("summary", content);
	    	list.add(map);
	    	cursor.moveToPrevious();
		}
		SimpleAdapter adapter=new SimpleAdapter(context, list, R.layout.note_item,
				new String[]{"title","date","summary"},
				new int[]{R.id.note_item_title,R.id.note_item_date,R.id.note_item_summary});
		
		
		
		return adapter;
	}

	public SimpleAdapter getLiftAdapter() {
		// TODO Auto-generated method stub
List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		
		MySQLiteHelper helper=new MySQLiteHelper(context, "user_notebook", null, 4);
		SQLiteDatabase db= helper.getReadableDatabase();
		String sql="select * from user_notebook where name=? and type='1"
				+ "'";
		String name=context.getSharedPreferences("localSave", context.MODE_WORLD_READABLE ).getString("name", "");
		Cursor cursor= db.rawQuery(sql, new String[]{name});
		cursor.moveToLast();
		while(cursor.moveToPrevious()){
			cursor.moveToNext();
			String nam=cursor.getString(2);
	    	String title=cursor.getString(3);
	    	String content=cursor.getString(4);
	    	String date=cursor.getString(5);
	    	if(content.length()>30){
	    		content=content.substring(0, 30);
	    	}
	    	Map<String,String> map=new HashMap<String,String>();
	    	map.put("title", title);
	    	map.put("date", "by "+nam+' '+date);
	    	//summary显示前30个字符
	    	map.put("summary", content);
	    	list.add(map);
	    	cursor.moveToPrevious();
		}
		SimpleAdapter adapter=new SimpleAdapter(context, list, R.layout.note_item,
				new String[]{"title","date","summary"},
				new int[]{R.id.note_item_title,R.id.note_item_date,R.id.note_item_summary});
		
		
		
		return adapter;
	}

	public SimpleAdapter getWorkAdapter() {
		// TODO Auto-generated method stub
List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		
		MySQLiteHelper helper=new MySQLiteHelper(context, "user_notebook", null,4);
		SQLiteDatabase db= helper.getReadableDatabase();
		String sql="select * from user_notebook where name=? and type=?";
		String name=context.getSharedPreferences("localSave", context.MODE_WORLD_READABLE ).getString("name", "");
		Cursor cursor= db.rawQuery(sql, new String[]{name,"2"});
		//未完待续
		cursor.moveToLast();
		while(cursor.moveToPrevious()){
			cursor.moveToNext();
			String nam=cursor.getString(2);
	    	String title=cursor.getString(3);
	    	String content=cursor.getString(4);
	    	String date=cursor.getString(5);
	    	if(content.length()>30){
	    		content=content.substring(0, 30);
	    	}
	    	Map<String,String> map=new HashMap<String,String>();
	    	map.put("title", title);
	    	map.put("date", "by "+nam+' '+date);
	    	//summary显示前30个字符
	    	map.put("summary", content);
	    	list.add(map);
	    	cursor.moveToPrevious();
		}
		SimpleAdapter adapter=new SimpleAdapter(context, list, R.layout.note_item,
				new String[]{"title","date","summary"},
				new int[]{R.id.note_item_title,R.id.note_item_date,R.id.note_item_summary});
		
		
		
		return adapter;
		}

	public SimpleAdapter getOtherAdapter() {
		// TODO Auto-generated method stub
List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		
		MySQLiteHelper helper=new MySQLiteHelper(context, "user_notebook", null, 4);
		SQLiteDatabase db= helper.getReadableDatabase();
		String sql="select * from user_notebook where name=? and type=?";
		String name=context.getSharedPreferences("localSave", context.MODE_WORLD_READABLE ).getString("name", "");
		Cursor cursor= db.rawQuery(sql, new String[]{name,"3"});
		//未完待续
		cursor.moveToLast();
		while(cursor.moveToPrevious()){
			cursor.moveToNext();
			String nam=cursor.getString(2);
	    	String title=cursor.getString(3);
	    	String content=cursor.getString(4);
	    	String date=cursor.getString(5);
	    	if(content.length()>30){
	    		content=content.substring(0, 30);
	    	}
	    	Map<String,String> map=new HashMap<String,String>();
	    	map.put("title", title);
	    	map.put("date", "by "+nam+' '+date);
	    	//summary显示前30个字符
	    	map.put("summary", content);
	    	list.add(map);
	    	cursor.moveToPrevious();
	    	//System.out.println("查询到："+title+'\n'+date+'\n'+content);
		}
		SimpleAdapter adapter=new SimpleAdapter(context, list, R.layout.note_item,
				new String[]{"title","date","summary"},
				new int[]{R.id.note_item_title,R.id.note_item_date,R.id.note_item_summary});
		
		
		
		return adapter;	}
}
