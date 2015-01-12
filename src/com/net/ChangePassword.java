package com.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.message.BufferedHeader;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ChangePassword extends Thread{

	JSONObject jo;
	Handler handler;
	public ChangePassword(Handler handler,String name,String oldPass,String newPass) {
		// TODO Auto-generated constructor stub
		jo=new JSONObject();
		this.handler=handler;
		try {
			jo.put("name", name);
			jo.put("pass", oldPass);
			jo.put("newPass", newPass);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		URL url;
		try {
			url = new URL(URLText.urlText+"ChangePassword");
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			OutputStream os= conn.getOutputStream();
			os.write(jo.toString().getBytes());
			os.flush();
			if(conn.getResponseCode()==200){
				BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				String jsonString="";
				while((line=br.readLine()) != null){
					jsonString+=line;
				}
				try {
					JSONObject joo=new JSONObject(new String(jsonString.getBytes(),"UTF-8"));
					if(joo.getBoolean("isChangedPass")){
						handler.sendEmptyMessage(1);
					}else{
						handler.sendEmptyMessage(0);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
