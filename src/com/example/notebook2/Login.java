package com.example.notebook2;

import com.service.HttpOperator;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	String name,pass;
	EditText Ename,Epass;
	HttpOperator httpO=new HttpOperator();
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
	    pass=Ename.getText().toString().trim();
	    
	    
	}

	public void login(View v){
		if(httpO.login(name, pass)){
			//��½�ɹ�����
			Intent intent=new Intent(Login.this,Main.class);
			intent.putExtra("name", name);
			intent.putExtra("pass", pass);
			startActivity(intent);
		}
		else{
			//��½ʧ�ܴ���
			Toast.makeText(Login.this, "�û������������", Toast.LENGTH_SHORT).show();
		}
	}
	public void forgetPass(View v){
		final EditText view=new EditText(Login.this);
		view.setHint("ע������");
		new AlertDialog.Builder(Login.this).setTitle("�һ�����")
		.setView(view)
		.setIcon(R.drawable.ic_launcher)
		.setNegativeButton("ȡ��", null)
		
		.setPositiveButton("ȷ��", new OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String email=view.getText().toString().trim();
				boolean sendEmailSuccess= httpO.findPass(email);
				if(sendEmailSuccess){
					Toast.makeText(Login.this, "��ȷ���ʼ�", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(Login.this, "��������ϵ����ԱQQ��872988104", Toast.LENGTH_LONG).show();
				}
			}}).show();
	}
	public void register(View v){
		Intent intent=new Intent(Login.this,Register.class);
		startActivity(intent);
	}
	
}
