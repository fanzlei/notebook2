package com.net;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.utils.JsonUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class CheckUser  extends Thread{

	Context context;
	HttpClient httpClient=new DefaultHttpClient();
	HttpResponse response;
	JsonUtils jsonUtils=new JsonUtils();
	String name;
	String pass;
	boolean isChecked=false;
	Handler handler;
	
	public CheckUser(Handler handler,String name,String pass){
		this.handler=handler;
		this.name=name;
		this.pass=pass;
	}
	
	

	public void run() {
		// TODO Auto-generated method stub
		String path="http://192.168.0.108:8080/Notebook2_service/Login?name="+name+"&pass="+pass+"";
		HttpGet get=new HttpGet(path);
		try {
			
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,  2000);
		    //HttpClient连接超时直接退出线程
			response=httpClient.execute(get);
			if(response.getStatusLine().getStatusCode()==200){
			InputStream is=response.getEntity().getContent();
			Message msg=new Message();
			msg.what=0x123;
			msg.obj=jsonUtils.checkUser(is);
			httpClient.getConnectionManager().shutdown();
			handler.sendMessage(msg);
		    
			}
				
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("服务器连接错误");
            handler.sendEmptyMessage(0x124);
		}
	}



	
}
