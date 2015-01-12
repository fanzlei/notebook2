package com.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.net.URLText;

import android.os.Handler;
import android.os.Message;

public class DeleteNoteToServer extends Thread{

	List<String> list;
	Handler handler;
	final int DELETESUCCESS=0x123;
	final int DELETEFAIL=0x124;
	public DeleteNoteToServer(Handler handler,List<String> list) {
		// TODO Auto-generated constructor stub
		this.handler=handler;
		this.list=list;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		JSONArray ja=new JSONArray();
		for(int i=0;i<list.size();i++){
			try {
				JSONObject jo=new JSONObject();
				jo.put("serverId", list.get(i));
				ja.put(jo);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();}}
		URL url;
		try {
			url = new URL(URLText.urlText+"DeleteNote");
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(2000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			OutputStream os=conn.getOutputStream();
			os.write(ja.toString().getBytes());
			os.flush();
			if(conn.getResponseCode()==200){
				//��������������
				handler.sendEmptyMessage(DELETESUCCESS);
				
			}else{handler.sendEmptyMessage(DELETEFAIL);}
			os.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
