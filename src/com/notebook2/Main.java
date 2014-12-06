package com.notebook2;

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
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
	GestureDetector detector;
	ListView noteList;
	
	ListView menuList;
	int selectedPosition=100;
	boolean isLogined=false;
	JsonUtils jsonUtils=new JsonUtils();
	String name;
	String pass;
	int REQUEST_CODE=0x321;
	ListListener listListener;
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
		SharedPreferences sp=this.getSharedPreferences("localSave", MODE_WORLD_READABLE);
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






	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUEST_CODE&&resultCode==0){
			//由登陆界面跳转到本Activity
			
		}
		
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
			System.exit(0);
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
	public void sync(View v){
		if(isLogined){
			Toast.makeText(Main.this, "开始同步", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent("com.fanz.syncService");
			intent.putExtra("name", name);
			startService(intent);
		}else{
			Toast.makeText(Main.this, "请先登陆", Toast.LENGTH_SHORT).show();
		}
	}
	
}
