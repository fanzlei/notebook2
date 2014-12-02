package com.notebook2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AccountInformation extends Activity{

	public AccountInformation() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView tv=new TextView(this);
		tv.setText("用户信息界面");
		setContentView(tv);
		
	}

}
