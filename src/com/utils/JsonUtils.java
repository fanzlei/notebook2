package com.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

	
	public JsonUtils() {
		// TODO Auto-generated constructor stub
	}

	public boolean checkUser(InputStream is){
		String JsonString=inputStreamToString(is);
		try {
			JSONObject jb=new JSONObject(JsonString);
			System.out.println("服务器返回密码验证结果："+jb.getBoolean("isChecked"));
			return jb.getBoolean("isChecked");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	private String inputStreamToString(InputStream is){
		ByteArrayOutputStream os=new ByteArrayOutputStream();
		byte[] data=new byte[1024];
		int len=0;
		try {
			while((len=is.read(data))!=-1){
				os.write(data,0,data.length);
			}
			return new String(os.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	public boolean checkRegister(InputStream is) {
		String jsonString=inputStreamToString(is);
		try {
			JSONObject jo=new JSONObject(jsonString);
			System.out.println(jo.getBoolean("isRegisted"));
			return jo.getBoolean("isRegisted");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
		// TODO Auto-generated method stub
	}

	
	
}
