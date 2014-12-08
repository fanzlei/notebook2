package com.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.net.SaveToServer;
import com.utils.JsonUtils;
import com.utils.MySQLiteHelper;
import com.utils.MySQLiteUtils;
import com.utils.Note;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class SyncService extends Service{

    String name;
    SQLiteDatabase db;
    MySQLiteUtils mysqlUtils;
    SharedPreferences sp;
    final int DELETESUCCESS=0x123;
    final int DELETEFAIL=0x124;
    //保存本地删除的note的serverId，当同步时候用来向服务器发送请求删除对应note
    List<String> list=new ArrayList<String>();
    Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case DELETESUCCESS:
				System.out.println("delete success");
				new GetAllNoteFromServer(handler, SyncService.this, name).start();
				break;
			case DELETEFAIL:
				System.out.println("delete fail");
				break;
			
			}
			
		}
    	
    };
	public class MyBinder extends Binder{
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		db=new MySQLiteHelper(this, "user_notebook", null,4).getReadableDatabase();
		sp= this.getSharedPreferences("localSave", this.MODE_WORLD_READABLE);
		name=sp.getString("name", "");
		mysqlUtils=new MySQLiteUtils(this);
		System.out.println("service created");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Set set= sp.getStringSet("deletedNote", new HashSet<String>());
		Iterator it= set.iterator();
		while(it.hasNext()){
			String str= (String) it.next();
			list.add(str);
			System.out.println("got serverId:"+str+"\n");
		}
		new DeleteNoteToServer(handler, list).start();
		//把本地没有serverId的note上传到服务器
		String sql="select * from user_notebook where name='"+name+"' and serverid=''";
		Cursor cursor=db.rawQuery(sql, null);
		System.out.println("本地没有serverId的note数量："+cursor.getCount());
		while(cursor.moveToNext()){
			Note note =new Note();
			note.setContent(cursor.getString(4));
			note.setDate(cursor.getString(5));
			note.setTitle(cursor.getString(3));
			note.setType(Integer.valueOf(cursor.getString(6)));
			note.setUser_name(name);
			new SaveToServer(note, this).start();
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
}
