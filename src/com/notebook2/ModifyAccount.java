package com.notebook2;

import org.json.JSONException;
import org.json.JSONObject;

import com.fanz.notebook2.R;
import com.net.ChangePassword;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyAccount extends Activity{

	EditText oldPass,newPass,checkNewPass;
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==0){
				//修改密码失败
				Toast.makeText(ModifyAccount.this, "修改密码失败", Toast.LENGTH_SHORT).show();
				SharedPreferences sp=ModifyAccount.this.getSharedPreferences("localSave",
						ModifyAccount.MODE_WORLD_READABLE);
				SharedPreferences.Editor editor=sp.edit();
				editor.putString("pass", newPass.getText().toString().trim());
				editor.commit();
			}else{
				//修改密码成功
				Toast.makeText(ModifyAccount.this, "修改密码成功", Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(ModifyAccount.this,Main.class);
				ModifyAccount.this.startActivity(intent);
				ModifyAccount.this.finish();
			}
		}
	};
	public ModifyAccount() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_account);
		oldPass=(EditText) findViewById(R.id.oldPassword);
		newPass=(EditText) findViewById(R.id.newPassword);
		checkNewPass=(EditText) findViewById(R.id.CheckNewPassword);
		
	}

	public void changePass(View v){
		SharedPreferences sp=this.getSharedPreferences("localSave", this.MODE_WORLD_READABLE);
		String name=sp.getString("name", "");
		String OldPass=oldPass.getText().toString().trim();
		String NewPass=newPass.getText().toString().trim();
		String CheckNewPass=checkNewPass.getText().toString().trim();
		if(NewPass.equals(CheckNewPass) && NewPass.length()>3){
			new ChangePassword(handler,name,OldPass,NewPass).start();
		}else{
			Toast.makeText(this, "两次输入密码不一致或密码长度小于4", Toast.LENGTH_SHORT).show();
		}
	}
}
