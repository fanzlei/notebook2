package com.notebook2;

import com.fanz.notebook2.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class AccountInformation extends Activity{

	TextView n,m,p,e;
	public AccountInformation() {
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_information);
		n=(TextView) findViewById(R.id.account_n);
		m=(TextView) findViewById(R.id.account_m);
		p=(TextView) findViewById(R.id.account_p);
		e=(TextView) findViewById(R.id.account_e);
		SharedPreferences sp=this.getSharedPreferences("localSave",MODE_WORLD_READABLE);
		n.setText(sp.getString("name", "Î´µÇÂ½"));
		m.setText("****");
		p.setText(sp.getString("phone", ""));
		e.setText(sp.getString("email", ""));
	}

}
