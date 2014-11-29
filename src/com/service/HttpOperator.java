package com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class HttpOperator {

	HttpClient httpClient;
	HttpResponse response;
	public HttpOperator(){
		httpClient=new DefaultHttpClient();
	}
	public boolean register(String name,String pass,String phone,String email){
	//◊¢≤·”√ªß	
		HttpPost post=new HttpPost("http://192.168.0.108:8080/Login");
		try {
	        List<NameValuePair> params=new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("name",name));
	        params.add(new BasicNameValuePair("pass",pass));
			HttpEntity entity=new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(entity);
			response=httpClient.execute(post);
			
			
			
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
   //µ«¬Ω
		
		return false;
	}
	public boolean findPass(String email){
    //’“ªÿ√‹¬Î
		
		return false;
		}
}
