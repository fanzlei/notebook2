package com.notebook2;

import com.fanz.notebook2.R;

import android.content.Intent;
import android.content.SharedPreferences;
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
				SharedPreferences sp= ConfigurationActivity.this.getSharedPreferences("localSave", ConfigurationActivity.this.MODE_WORLD_READABLE);
				SharedPreferences.Editor e=sp.edit();
				e.putBoolean("syncWIFIOnly", (Boolean) newValue);
				e.commit();
				return true;
			}});
      /*  Preference syncType=this.findPreference("listPreference");
        syncType.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				// TODO Auto-generated method stub
				SharedPreferences sp= ConfigurationActivity.this.getSharedPreferences("localSave", ConfigurationActivity.this.MODE_WORLD_READABLE);
				SharedPreferences.Editor e=sp.edit();
				e.putBoolean("syncType", (Boolean) newValue);//自动为0，手动为1
				e.commit();
				
				return true;
			}});*/
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
