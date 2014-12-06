package com.net;

import java.io.IOException;
import java.io.InputStream;
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
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.utils.JsonUtils;

public class RegisterThread extends Thread{
	String name;
	String pass;
	String phone;
	String email;
	HttpClient httpClient=new DefaultHttpClient();
	HttpResponse response;
	JsonUtils jsonUtils=new JsonUtils();
	Context context;
	Handler handler;
	public RegisterThread(Context context,Handler handler,String name,String pass,String phone,String email) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.pass=pass;
		this.phone=phone;
		this.email=email;
		this.context=context;
		this.handler=handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpPost post=new HttpPost("http://192.168.0.108:8080/Notebook2_service/Register");
		try {
	        List<NameValuePair> params=new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("name",name));
	        params.add(new BasicNameValuePair("pass",pass));
	        params.add(new BasicNameValuePair("phone",phone));
	        params.add(new BasicNameValuePair("email",email));
			HttpEntity entity=new UrlEncodedFormEntity(params,HTTP.UTF_8);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,  2000);
			post.setEntity(entity);
			response=httpClient.execute(post);
			if(response.getStatusLine().getStatusCode()==200){
				InputStream is=response.getEntity().getContent();
				
				Message msg=new Message();
				msg.what=0x123;
				msg.obj=jsonUtils.checkRegister(is);
				handler.sendMessage(msg);
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			handler.sendEmptyMessage(0x124);
		}
	}

	
}
