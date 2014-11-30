package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpOperator {

	HttpClient httpClient;
	HttpResponse response;
	JsonUtils jsonUtils;
	public HttpOperator(){
		httpClient=new DefaultHttpClient();
	    jsonUtils=new JsonUtils();
	}
	public boolean register(String name,String pass,String phone,String email){
	//×¢²áÓÃ»§	
		HttpPost post=new HttpPost("http://192.168.0.108:8080/Login");
		try {
	        List<NameValuePair> params=new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("name",name));
	        params.add(new BasicNameValuePair("pass",pass));
	        params.add(new BasicNameValuePair("phone",phone));
	        params.add(new BasicNameValuePair("email",email));
			HttpEntity entity=new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(entity);
			response=httpClient.execute(post);
			//Î´Íê´ýÐø
			
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean login(String name,String pass){
   //µÇÂ½
		String path="http://192.168.0.108:8080/Notebook2_service/Login?name='"+name+"'&pass='"+pass+"'";
		URL url;
		try {
			url = new URL(path);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();
            if(conn.getResponseCode()==200){
            	InputStream inputStream=conn.getInputStream();
                return jsonUtils.checkUser(inputStream);
            }else{return false;}  
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	public boolean findPass(String email){
    //ÕÒ»ØÃÜÂë
		
		return false;
		}
}
