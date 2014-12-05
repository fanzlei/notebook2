package com.notebook2;

import com.fanz.notebook2.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.appcompat.R.color;
import android.view.View;
import android.widget.TextView;

public class Account extends Activity{

	public Account() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	public void click(View v){
		switch(v.getId()){
		case R.id.account_infromation:
			Intent intent=new Intent(this,AccountInformation.class);
			startActivity(intent);
			break;
		case R.id.account_logout:
			Intent intent2=new Intent(this,Login.class);
			startActivity(intent2);
			break;
		case R.id.account_change_password:
			Intent in=new Intent(this,ModifyAccount.class);
			startActivity(in);
			break;
		
		}
		
	}
}
