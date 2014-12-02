package com.notebook2;


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
import org.apache.http.protocol.HTTP;

import com.fanz.notebook2.R;
import com.utils.JsonUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity{

	String name,pass,checkPass,phone,email;
	TextView errorMsg;
	StringBuilder error=new StringBuilder();
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==0x123){
				if((Boolean)msg.obj){
					Intent intent =new Intent(Register.this,Login.class);
					
					SharedPreferences sp=getSharedPreferences("localSave",MODE_WORLD_READABLE );
					SharedPreferences.Editor editor=sp.edit();
					editor.putString("name", name);
					editor.putString("pass", pass);
					editor.commit();
					startActivity(intent);
					
				}else{
					Toast.makeText(Register.this, "�û����Ѿ�����", Toast.LENGTH_SHORT).show();
				}
			}
			
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.register);
		name=((EditText) findViewById(R.id.name)).getText().toString().trim();
		pass=((EditText) findViewById(R.id.pass)).getText().toString().trim();
		checkPass=((EditText) findViewById(R.id.checkPass)).getText().toString().trim();
		phone=((EditText) findViewById(R.id.phone)).getText().toString().trim();
		email=((EditText) findViewById(R.id.email)).getText().toString().trim();
		errorMsg=(TextView) findViewById(R.id.ErrorMsg);
		EditText EcheckPass=(EditText) findViewById(R.id.checkPass);
		EcheckPass.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus){
					pass=((EditText) findViewById(R.id.pass)).getText().toString().trim();
					checkPass=((EditText) findViewById(R.id.checkPass)).getText().toString().trim();
					if(!pass.equals(checkPass)){
						error.append("\n�����������벻��ͬ");
						errorMsg.setText(error);
						
					}
					if(pass.equals(checkPass)){
						error.delete(0, error.length());
						errorMsg.setText(error);
					}
				}
			}});
	}

	public void register(View v){
		name=((EditText) findViewById(R.id.name)).getText().toString().trim();
		pass=((EditText) findViewById(R.id.pass)).getText().toString().trim();
		checkPass=((EditText) findViewById(R.id.checkPass)).getText().toString().trim();
		phone=((EditText) findViewById(R.id.phone)).getText().toString().trim();
		email=((EditText) findViewById(R.id.email)).getText().toString().trim();
		//�����ʽ��ȷ
		if(name.length()>2 && pass.length()>3 &&phone.length()==11&&
				email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
				&&pass.equals(checkPass)){
			new RegisterThread(name,pass,phone,email).start();
		}else{
			error.delete(0, error.length());
			if(name.length()<=2){
				error.append("\n���ֳ�����Ҫ����2");
				errorMsg.setText(error);
			}
			if(pass.length()<=3){
				error.append("\n���볤����Ҫ����3");
				errorMsg.setText(error);
			}
			if(phone.length()!=11){
				error.append("\n�ֻ�������Ҫ11λ");
				errorMsg.setText(error);
			}
			if(!email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
				error.append("\n�����ʽ������");
				errorMsg.setText(error);
			}
			if(!pass.equals(checkPass)){
				error.append("\n�����������벻��ͬ");
				errorMsg.setText(error);
			}
		}
	}
	private class RegisterThread extends Thread{

		String name;
		String pass;
		String phone;
		String email;
		HttpClient httpClient=new DefaultHttpClient();
		HttpResponse response;
		JsonUtils jsonUtils=new JsonUtils();
		public RegisterThread(String name,String pass,String phone,String email) {
			// TODO Auto-generated constructor stub
			this.name=name;
			this.pass=pass;
			this.phone=phone;
			this.email=email;
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
			}
		}

		
	}
}