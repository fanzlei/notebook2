package com.example.notebook2;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

import com.service.HttpOperator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity{

	HttpOperator httpO=new HttpOperator();
	String name,pass,checkPass,phone,email;
	TextView errorMsg;
	StringBuilder error=new StringBuilder();
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
						error.append("\n两次输入密码不相同");
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
		//输入格式正确
		if(name.length()>2 && pass.length()>3 &&phone.length()==11&&email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
			boolean registed=httpO.register(name,pass,phone,email);
			if(registed){
				//注册成功
				Intent intent =new Intent(Register.this,Login.class);
				
				SharedPreferences sp=getSharedPreferences("localSave",MODE_WORLD_READABLE );
				SharedPreferences.Editor editor=sp.edit();
				editor.putString("name", name);
				editor.putString("pass", pass);
				editor.commit();
				startActivity(intent);
				
			}else{//注册失败，服务器出错
				Toast.makeText(Register.this, "注册失败，服务器出错", Toast.LENGTH_SHORT).show();
			}
		}else{
			error.delete(0, error.length());
			if(name.length()<=2){
				error.append("\n名字长度需要大于2");
				errorMsg.setText(error);
			}
			if(pass.length()<=3){
				error.append("\n密码长度需要大于3");
				errorMsg.setText(error);
			}
			if(phone.length()!=11){
				error.append("\n手机号码需要11位");
				errorMsg.setText(error);
			}
			if(!email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
				error.append("\n邮箱格式不符合");
				errorMsg.setText(error);
			}
		}
	}
}
