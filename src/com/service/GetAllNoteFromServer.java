package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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

import com.utils.JsonUtils;
import com.utils.MySQLiteHelper;
import com.utils.MySQLiteUtils;
import com.utils.Note;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

public class GetAllNoteFromServer extends Thread{

	Handler handler;
	final int GETALLNOTE_OK=0x125;
	final int GETALLNOTE_FAIL=0x126;
	MySQLiteUtils mysqlUtils;
	SQLiteDatabase db;
	String name;
	Context context;
	public GetAllNoteFromServer(Handler handler,Context context,String name) {
		// TODO Auto-generated constructor stub
		this.handler=handler;
		this.name=name;
		this.context=context;
		mysqlUtils=new MySQLiteUtils(context);
		db=new MySQLiteHelper(context, "user_notebook", null, 4).getReadableDatabase();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		//服务器删除note后才进行其他同步操作
		HttpClient client=new DefaultHttpClient();
		HttpPost post=new HttpPost("http://192.168.0.108:8080/Notebook2_service/Sync");
		List<NameValuePair> list=new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("name",name));
		try {
			HttpEntity entity=new UrlEncodedFormEntity(list,HTTP.UTF_8);
			post.setEntity(entity);
			HttpResponse res= client.execute(post);
			if(res.getStatusLine().getStatusCode()==200){
				InputStream is= res.getEntity().getContent();
				String jsonArrayString=new JsonUtils().inputStreamToString(is);
				System.out.println("jsonArrayString length="+jsonArrayString.length());
				JSONArray jsonArray=new JSONArray(jsonArrayString);
				System.out.println("has got JOSNArray from server,length="+jsonArray.length());
				for(int i=0;i<jsonArray.length();i++){
					//循环每个服务器传过来的note，本地没有则保存到本地，
					//本地有且本地note的date和服务器的note的date不一致，即本地note有改动
					//向服务器更新该note
					JSONObject jo=jsonArray.getJSONObject(i);
					String sql="select * from user_notebook where serverid="+jo.getInt("serverId")+"";
					Cursor cursor=db.rawQuery(sql, null);
					System.out.println("查询到serverId为+"+jo.getInt("serverId")+"+的结果数："+cursor.getCount());
					cursor.moveToNext();
					if(cursor.getCount()<1){
						//没有找到本地对应note,把服务器的note保存到本地
						System.out.println("no matches note local");
						Note note=new Note();
						note.setContent(jo.getString("content"));
						note.setDate(jo.getString("date"));
						note.setServerId(jo.getString("serverId"));
						note.setTitle(jo.getString("title"));
						note.setType(Integer.parseInt(jo.getString("type")));
						note.setUser_name(name);
						mysqlUtils.saveNote(note);
						System.out.println("把服务器的note保存到本地成功，serverId为："+jo.getString("serverId"));
					}else if(cursor.getString(5)!=jo.getString("date")){
						//本地有该serverId的note，如果本地的note有改动，更新服务器对应的note
						System.out.println("服务器note的date为："+jo.getString("date")+"\n本地note的date为："+cursor.getString(5)+"\n日期不匹配，开始向服务器同步");
						
						JSONObject joo=new JSONObject();
						joo.put("name", name);
						joo.put("title", cursor.getString(3));
						joo.put("content", cursor.getString(4));
						joo.put("date", cursor.getString(5));
						joo.put("type", cursor.getString(6));
						joo.put("serverId", cursor.getString(1));
						new UpdateNoteToServer(joo,context).start();
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

	
}
