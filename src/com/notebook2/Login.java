package com.notebook2;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONException;
import org.json.JSONObject;

import com.fanz.notebook2.R;
import com.net.CheckUser;
import com.utils.JsonUtils;
import com.utils.SendEmail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	String name,pass;
	EditText Ename,Epass;
    HttpResponse response=null;
    HttpClient httpClient=new DefaultHttpClient();
    JsonUtils jsonUtils=new JsonUtils();
    Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==0x123){
				JSONObject jo=new JSONObject();
				jo=(JSONObject) msg.obj;
				System.out.println("登陆成功，服务器返回jsonString:'"+jo.toString()+"'");
				try {
					if(jo.getBoolean("isChecked")){
						SharedPreferences sp=getSharedPreferences("localSave",MODE_WORLD_READABLE );
						SharedPreferences.Editor editor=sp.edit();
						editor.putString("name", name);
						editor.putString("pass", pass);
						editor.putString("phone", jo.getString("phone"));
						editor.putString("email", jo.getString("email"));
						editor.commit();
						Intent intent=new Intent(Login.this,Main.class);
						intent.putExtra("name", name);
						Login.this.setResult(0, intent);
						Intent intnt=new Intent(Login.this,Main.class);
						startActivity(intnt);
						Login.this.finish();
					}else{
						Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
						Uri uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
						Ringtone r=RingtoneManager.getRingtone(Login.this, uri);
						r.play();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
			
		};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	    Ename=(EditText) findViewById(R.id.name);
	    Epass=(EditText) findViewById(R.id.pass);
	    SharedPreferences sp= getSharedPreferences("localSave", MODE_WORLD_WRITEABLE);
	    String na=sp.getString("name", "");
	    String pa=sp.getString("pass", "");
	    Ename.setText(na);
	    Epass.setText(pa);
	    name=Ename.getText().toString().trim();
	    pass=Epass.getText().toString().trim();
	    
	  
	}

	public void login(View v){
		 name=Ename.getText().toString().trim();
		    pass=Epass.getText().toString().trim();
		if(!name.isEmpty()&&name!=null&&!pass.isEmpty()&&pass!=null){
			new CheckUser(handler,name,pass).start();
		}else{
			Toast.makeText(Login.this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
		}
		
	}
	public void forgetPass(View v){
		final EditText view=new EditText(Login.this);
		view.setHint("注册邮箱");
		new AlertDialog.Builder(Login.this).setTitle("找回密码")
		.setView(view)
		.setIcon(R.drawable.ic_launcher)
		.setNegativeButton("取消", null)
		
		.setPositiveButton("确定", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String email=view.getText().toString().trim();
				if(email.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")){
					SendEmail send=new SendEmail();
					boolean sendEmailSuccess=send.sendEmail(email);
					if(sendEmailSuccess){
						Toast.makeText(Login.this, "请确认邮件", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(Login.this, "错误，请联系管理员QQ：872988104", Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(Login.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
				}
				
			}}).show();
	}
	public void register(View v){
		Intent intent=new Intent(Login.this,Register.class);
		startActivity(intent);
	}
	
}
