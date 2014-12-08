package com.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.widget.Toast;

public class UpdateNoteToServer extends Thread{

	JSONObject jsonObject;
	Context context;
	public UpdateNoteToServer(JSONObject jsonObject,Context context) {
		// TODO Auto-generated constructor stub
		this.jsonObject=jsonObject;
		this.context=context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		URL url;
		try {
			url = new URL("http://192.168.0.108:8080/Notebook2_service/UpdateNote");
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(2000);
			conn.setRequestMethod("POST");
			OutputStream os=conn.getOutputStream();
			os.write(jsonObject.toString().getBytes());
			os.flush();
			if(conn.getResponseCode()==200){
				System.out.println("���·�������note�ɹ�");
				Intent intent=new Intent();
				intent.setAction("com.fanz.notebook.sync");
				intent.putExtra("date", new Date(System.currentTimeMillis()).toLocaleString());
				context.sendBroadcast(intent);
				Looper.prepare();
				Toast.makeText(context, "ͬ�����", Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
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
