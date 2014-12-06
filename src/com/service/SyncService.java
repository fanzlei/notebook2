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
import android.os.IBinder;
import android.util.Log;

public class SyncService extends Service{

    String name;
    SQLiteDatabase db;
    MySQLiteUtils mysqlUtils;
    SharedPreferences sp;
    //���汾��ɾ����note��serverId����ͬ��ʱ���������������������ɾ����Ӧnote
    String[] deletedNote=new String[]{};
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
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Set set= sp.getStringSet("deletedNote", new HashSet<String>());
		deletedNote= (String[]) set.toArray();
		int deleteSuccessCode=deleteNoteToService(deletedNote);
		if(deleteSuccessCode==200){
			//������ɾ��note��Ž�������ͬ������
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost("http://192.168.0.108:8080/Notebook2_service/Sync");
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("name",name));
		try {
			HttpEntity entity=new UrlEncodedFormEntity(list,HTTP.UTF_8);
			HttpResponse res= client.execute(post);
			if(res.getStatusLine().getStatusCode()==200){
				InputStream is= res.getEntity().getContent();
				String jsonArrayString=new JsonUtils().inputStreamToString(is);
				JSONArray jsonArray=new JSONArray(jsonArrayString);
				for(int i=0;i<jsonArray.length();i++){
					//ѭ��ÿ����������������note������û���򱣴浽���أ�
					//�������ұ���note��date�ͷ�������note��date��һ�£�������note�иĶ�
					//����������¸�note
					JSONObject jo=jsonArray.getJSONObject(i);
					String sql="select * from user_notebook where serverid='"+jo.getInt("serverId")+"'";
					Cursor cursor=db.rawQuery(sql, null);
					if(cursor.getCount()<1){
						//û���ҵ����ض�Ӧnote,�ѷ�������note���浽����
						Note note=new Note();
						note.setContent(jo.getString("content"));
						note.setDate(jo.getString("date"));
						note.setServerId(jo.getString("serverId"));
						note.setTitle(jo.getString("title"));
						note.setType(Integer.parseInt(jo.getString("type")));
						note.setUser_name(name);
						mysqlUtils.saveNote(note);
					}else{
						//�����и�serverId��note��������ص�note�иĶ������·�������Ӧ��note
						cursor.moveToNext();
						JSONObject joo=new JSONObject();
						joo.put("name", name);
						joo.put("title", cursor.getString(3));
						joo.put("content", cursor.getString(4));
						joo.put("date", cursor.getString(5));
						joo.put("type", cursor.getString(6));
						updateNoteToService(joo);
					}
				}
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void updateNoteToService(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		URL url;
		try {
			url = new URL("http://192.168.0.108:8080/Notebook_service/UpdateNote");
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(2000);
			conn.setRequestMethod("POST");
			conn.getOutputStream().write(jsonObject.toString().getBytes());
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int deleteNoteToService(String[] array){
		JSONArray ja=new JSONArray();
		for(int i=0;i<array.length;i++){
			try {
				JSONObject jo=new JSONObject();
				jo.put("serverId", array[i]);
				ja.put(jo);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}}
		URL url;
		try {
			url = new URL("http://192.168.0.108:8080/Notebook_service/DeleteNote");
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(2000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			OutputStream os=conn.getOutputStream();
			os.write(ja.toString().getBytes());
			if(conn.getResponseCode()==200){
				return 200;
			}
			os.flush();
			os.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	
}
