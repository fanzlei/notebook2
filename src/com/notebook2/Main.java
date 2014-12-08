package com.notebook2;

import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.adapter.MyAdapter;
import com.fanz.notebook2.R;
import com.listener.ListListener;
import com.net.CheckUser;
import com.utils.JsonUtils;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements  OnItemClickListener{

	LinearLayout rightPage;
	RelativeLayout leftPage;
	float screenWidth;
	boolean isShowLeft=false;
	boolean isReadyExit=false;
	ListView noteList;
	
	ListView menuList;
	int selectedPosition=100;
	boolean isLogined=false;
	JsonUtils jsonUtils=new JsonUtils();
	String name;
	String pass;
	int REQUEST_CODE=0x321;
	ListListener listListener;
	boolean isWIFI;
	static TextView syncDate;
	static SharedPreferences sp;
	//保存当前显示的List是哪个，当从其他Activity回到这里是就显示原来的List
	int nowShowList=0;
	public final  Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==0x123){
				//登陆验证
					JSONObject jo=new JSONObject();
					jo=(JSONObject) msg.obj;
					try {
						if(jo.getBoolean("isChecked")){
							isLogined=true;
							SharedPreferences.Editor e=sp.edit();
							e.putBoolean("isLogined", true);
							e.commit();
							Toast.makeText(Main.this, "登陆成功", Toast.LENGTH_SHORT).show();
							
						}else{
							Toast.makeText(Main.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
							Uri uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
							Ringtone r=RingtoneManager.getRingtone(Main.this, uri);
							r.play();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}
			if(msg.what==0x124){
				Toast.makeText(Main.this, "服务器错误", Toast.LENGTH_SHORT).show();
				Uri uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				Ringtone r=RingtoneManager.getRingtone(Main.this, uri);
				r.play();}	
			}	
    };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		leftPage=(RelativeLayout) findViewById(R.id.leftPage);
		rightPage=(LinearLayout) findViewById(R.id.rightPage);
		DisplayMetrics metrics=new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		screenWidth=metrics.widthPixels;
		ImageButton showLeft=(ImageButton) findViewById(R.id.showLeft);
		//侧滑菜单设置监听器和适配器
		menuList=(ListView)findViewById(R.id.menuList);
		menuList.setAdapter(new MyAdapter(this).getMenuAdapter());
		menuList.setOnItemClickListener(this);
		noteList=(ListView) findViewById(R.id.note_list);
		syncDate=(TextView) findViewById(R.id.syncDate);
	    sp=getSharedPreferences("localSave", MODE_WORLD_READABLE);
        syncDate.setText("上次同步:"+sp.getString("syncDate", ""));
      
        
		/*SimpleAdapter adapter= new MyAdapter(Main.this).getMyListAdapter();
		noteList.setAdapter(adapter);
		nowShowList=0;
		listListener=new ListListener(this,nowShowList);
		noteList.setOnItemClickListener(listListener);
		noteList.setOnItemLongClickListener(listListener);*/
		showLeft.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showLeftPage();
			}});
		name=sp.getString("name", "");
		pass=sp.getString("pass", "");
		new CheckUser(handler,name,pass).start();
		
		
	}
	
	
		
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SimpleAdapter adapter= new MyAdapter(Main.this).getMyListAdapter();
		noteList.setAdapter(adapter);
		nowShowList=0;
		listListener=new ListListener(this,nowShowList);
		noteList.setOnItemClickListener(listListener);
		noteList.setOnItemLongClickListener(listListener);
	}

	public void showRightPage(){
		if(isShowLeft){
			isShowLeft=false;
			int endValue=(int)(screenWidth-200);
			final ValueAnimator anim=ValueAnimator.ofInt(endValue,0);
			anim.setDuration(500);
			anim.addUpdateListener(new AnimatorUpdateListener(){

				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					// TODO Auto-generated method stub
					LayoutParams params= leftPage.getLayoutParams();
					params.width=(Integer) anim.getAnimatedValue();
					leftPage.setLayoutParams(params);
					
				}});
			anim.start();
			
		}
		
	}
	public void showLeftPage(){
		isShowLeft=true;
		int endValue=(int)(screenWidth-200);
		final ValueAnimator anim=ValueAnimator.ofInt(0,endValue);
		anim.setDuration(500);
		anim.addUpdateListener(new AnimatorUpdateListener(){

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				LayoutParams params= leftPage.getLayoutParams();
				params.width=(Integer) anim.getAnimatedValue();
				leftPage.setLayoutParams(params);
				
			}});
		anim.start();
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode==KeyEvent.KEYCODE_BACK&&isShowLeft){
			showRightPage();
			return true;
		}
		if(keyCode==KeyEvent.KEYCODE_BACK&&!isShowLeft&&!isReadyExit){
			Toast.makeText(Main.this, "再按一次退出！", Toast.LENGTH_SHORT).show();
			isReadyExit=true;
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					isReadyExit=false;
				}}, 2000);
			return true;
		}
		if(keyCode==KeyEvent.KEYCODE_BACK&&!isShowLeft&&isReadyExit){
			this.finish();
		}
		if(keyCode==KeyEvent.KEYCODE_MENU&&isShowLeft){
			showRightPage();
			return true;
		}
		if(keyCode==KeyEvent.KEYCODE_MENU&&!isShowLeft){
			showLeftPage();
			return true;
		}
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch(position){
		case 0:
			if(selectedPosition==0){showRightPage();break;}
			else{
				selectedPosition=0;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);
				SimpleAdapter adapter= new MyAdapter(Main.this).getMyListAdapter();
				noteList.setAdapter(adapter);
				nowShowList=0;
				listListener=new ListListener(this,nowShowList);
				noteList.setOnItemClickListener(listListener);
				noteList.setOnItemLongClickListener(listListener);
				break;
			}
			
		case 1:
			if(selectedPosition==1){showRightPage();break;}
			else{
				selectedPosition=1;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);				
				SimpleAdapter adapter= new MyAdapter(Main.this).getLiftAdapter();
				noteList.setAdapter(adapter);
				nowShowList=1;
				listListener=new ListListener(this,nowShowList);
				noteList.setOnItemClickListener(listListener);
				noteList.setOnItemLongClickListener(listListener);
				break;
			}
			
		case 2:
			if(selectedPosition==2){showRightPage();break;}
			else{
				selectedPosition=2;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);
				SimpleAdapter adapter= new MyAdapter(Main.this).getWorkAdapter();
				noteList.setAdapter(adapter);
				nowShowList=2;
				listListener=new ListListener(this,nowShowList);
				noteList.setOnItemClickListener(listListener);
				noteList.setOnItemLongClickListener(listListener);
				break;
			}
		case 3:
			if(selectedPosition==3){showRightPage();break;}
			else{
				selectedPosition=3;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);
				SimpleAdapter adapter= new MyAdapter(Main.this).getOtherAdapter();
				noteList.setAdapter(adapter);
				nowShowList=3;
				listListener=new ListListener(this,nowShowList);
				noteList.setOnItemClickListener(listListener);
				noteList.setOnItemLongClickListener(listListener);
				break;
			}
		case 4:
			if(selectedPosition==4){showRightPage();break;}
			else{
				selectedPosition=4;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);
				noteList.setAdapter(null);
				break;
			}
		case 5:
			if(selectedPosition==5){showRightPage();break;}
			else{
				selectedPosition=5;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);
				noteList.setAdapter(null);
				break;
			}
		
	}}

	private void setMenuItemSelectedBackgound(AdapterView<?> parent,
			int position) {
		// TODO Auto-generated method stub
		for(int i=0;i<6;i++){
			parent.getChildAt(i).setBackgroundResource(0);
		}
		parent.getChildAt(position).setBackgroundResource(R.drawable.menu_selected_bg);
	}	
	//显示设置界面
	public void showConfiguration(View v){
		Intent intent=new Intent(Main.this,ConfigurationActivity.class);
		startActivity(intent);
		
	}
	//显示添加记录界面
	public void showAdd(View v){
		Intent intent=new Intent(Main.this,AddActivity.class);
		startActivity(intent);
	}
	//执行同步操作
	public void startsync(View v){
		showRightPage();
		if(sp.getBoolean("isLogined", false)){
            if(sp.getBoolean("syncWIFIOnly", false)){
            	//系统设置为仅wifi同步
            	ConnectivityManager cm=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            	NetworkInfo info=cm.getActiveNetworkInfo();
            	if(info!=null){
            		if(info.getType()==ConnectivityManager.TYPE_WIFI){
                		//wifi已开
                		System.out.println("dfsdfsd");
                		Toast.makeText(Main.this, "开始同步", Toast.LENGTH_SHORT).show();
            			Intent intent=new Intent("com.fanz.syncService");
            			startService(intent);
            			return;
                	}else{Toast.makeText(Main.this, "仅wifi同步，当前wifi未开启", Toast.LENGTH_SHORT).show();return;}
            	}else{Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show();return;}
            }
            ConnectivityManager cm=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        	NetworkInfo info=cm.getActiveNetworkInfo();
        	if(info!=null){
        		 Toast.makeText(Main.this, "开始同步", Toast.LENGTH_SHORT).show();
     			Intent intent=new Intent("com.fanz.syncService");
     			startService(intent);return;
        	}else{Toast.makeText(this, "当前无网络", Toast.LENGTH_SHORT).show();}
           
		}else{
			Toast.makeText(Main.this, "请先登陆", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("onDestroy");
		SharedPreferences.Editor e=sp.edit();
		e.putBoolean("isLogined", false);
		e.commit();
		Intent intent=new Intent("com.fanz.syncService");
		stopService(intent);
		/*//根据设置来添加一个全局定时器用于后台自动同步 每过一小时自动同步一次，若某次同步完成，则在receive中取消该定时器；否则一直每小时执行直到同步完成
		AlarmManager am=(AlarmManager) this.getSystemService(Service.ALARM_SERVICE);
		Intent as=new Intent();
		as.setAction("com.fanz.syncService");
		PendingIntent pi=PendingIntent.getService(this,0, as, 0);
		am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, 0, 30*1000, pi);*/
	}

	public static class MyReceiver extends BroadcastReceiver{

		public MyReceiver() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
	        SharedPreferences.Editor editor=sp.edit();
	        editor.putString("syncDate", intent.getStringExtra("date"));
	        editor.putStringSet("deletedNote", new HashSet<String>());
	        editor.commit();
	        syncDate.setText("上次同步:"+sp.getString("syncDate", ""));
	      /*  AlarmManager am=(AlarmManager) context.getSystemService(Service.ALARM_SERVICE);
	        Intent as=new Intent();
			as.setAction("com.fanz.syncService");
			PendingIntent pi=PendingIntent.getService(context,0, as, 0);
			am.cancel(pi);*/
		}
	}
}
