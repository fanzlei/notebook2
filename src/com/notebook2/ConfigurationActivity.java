package com.notebook2;

import com.fanz.notebook2.R;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class ConfigurationActivity extends PreferenceActivity{

	public ConfigurationActivity() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preferences);
        Preference syncWIFIOnly= this.findPreference("syncWIFIOnly");
        syncWIFIOnly.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				// TODO Auto-generated method stub
				
				return true;
			}});
	}
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		// TODO Auto-generated method stub
		String title= (String) preference.getTitle();
		if(title.equals("账户信息")){
			Intent intent=new Intent(this,Account.class);
			startActivity(intent);
		}
		if(title.equals("关于我们")){
			Intent intent=new Intent(this,Versions.class);
			startActivity(intent);
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
}
