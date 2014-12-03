package com.notebook2;

import java.util.Timer;
import java.util.TimerTask;

import com.adapter.MyAdapter;
import com.fanz.notebook2.R;
import com.fragment.ImageFragment;
import com.fragment.MyListFragment;
import com.fragment.ShareFragment;
import com.fragment.StarFragment;
import com.fragment.TextFragment;
import com.fragment.VideoFragment;
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
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnGestureListener, OnItemClickListener{

	LinearLayout rightPage;
	RelativeLayout leftPage;
	float screenWidth;
	boolean isShowLeft=false;
	boolean isReadyExit=false;
	GestureDetector detector;
	MyListFragment listFragment=new MyListFragment();
	ImageFragment imageFragment=new ImageFragment();
	ShareFragment shareFragment=new ShareFragment();
	StarFragment starFragment=new StarFragment();
	TextFragment textFragment=new TextFragment();
	VideoFragment videoFragment=new VideoFragment();
	ListView menuList;
	int selectedPosition=100;
	ImageButton protrait;
	boolean isLogined=false;
	TextView main_user_name;
	JsonUtils jsonUtils=new JsonUtils();
	String name;
	String pass;
	public final  Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==0x123){
				//登陆验证
				if(msg.obj!=null){
					if((Boolean)msg.obj){
						isLogined=true;
						Toast.makeText(Main.this, "登陆成功", Toast.LENGTH_SHORT).show();
						
						main_user_name.setText(name);
					}else{
						Toast.makeText(Main.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
						Uri uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
						Ringtone r=RingtoneManager.getRingtone(Main.this, uri);
						r.play();
					}
					
				}else{
					Toast.makeText(Main.this, "服务器错误", Toast.LENGTH_SHORT).show();
					Uri uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
					Ringtone r=RingtoneManager.getRingtone(Main.this, uri);
					r.play();
				}
			}
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
		main_user_name=(TextView) findViewById(R.id.main_user_name);
		protrait=(ImageButton) findViewById(R.id.protrait);
		ImageButton showLeft=(ImageButton) findViewById(R.id.showLeft);
		//侧滑菜单设置监听器和适配器
		menuList=(ListView)findViewById(R.id.menuList);
		menuList.setAdapter(new MyAdapter(this).getMenuAdapter());
		menuList.setOnItemClickListener(this);
		showLeft.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showLeftPage();
			}});
		detector=new GestureDetector(this, this);
		SharedPreferences sp=this.getSharedPreferences("localSave", MODE_WORLD_READABLE);
		name=sp.getString("name", "");
		pass=sp.getString("pass", "");
		new CheckUser(handler,name,pass).start();
		protrait.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isLogined){
					Intent intent =new Intent(Main.this,AccountInformation.class);
					startActivity(intent);
				}else{
					Intent intent =new Intent(Main.this,Login.class);
					startActivity(intent);
				}
			}
			
		});
		main_user_name.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isLogined){
					Intent intent =new Intent(Main.this,AccountInformation.class);
					startActivity(intent);
				}else{
					Intent intent =new Intent(Main.this,Login.class);
					startActivity(intent);
				}
			}
			
		});
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
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getX()>(int)screenWidth-200){
			
			showRightPage();
		}
		
		return detector.onTouchEvent(event);
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
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		if(e2.getX()>e1.getX()){
			showLeftPage();
		}else{
			showRightPage();
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
				this.getFragmentManager().beginTransaction().replace(R.id.container, listFragment).commit();
				break;
			}
			
		case 1:
			if(selectedPosition==1){showRightPage();break;}
			else{
				selectedPosition=1;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);				
				getFragmentManager().beginTransaction().replace(R.id.container, textFragment).commit();
				break;
			}
			
		case 2:
			if(selectedPosition==2){showRightPage();break;}
			else{
				selectedPosition=2;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);
				getFragmentManager().beginTransaction().replace(R.id.container, videoFragment).commit();
				break;
			}
		case 3:
			if(selectedPosition==3){showRightPage();break;}
			else{
				selectedPosition=3;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);
				getFragmentManager().beginTransaction().replace(R.id.container, imageFragment).commit();
				break;
			}
		case 4:
			if(selectedPosition==4){showRightPage();break;}
			else{
				selectedPosition=4;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);
				getFragmentManager().beginTransaction().replace(R.id.container, starFragment).commit();
				break;
			}
		case 5:
			if(selectedPosition==5){showRightPage();break;}
			else{
				selectedPosition=5;
				showRightPage();
				setMenuItemSelectedBackgound(parent,position);
				getFragmentManager().beginTransaction().replace(R.id.container, shareFragment).commit();
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
		Toast.makeText(Main.this, "开始同步", Toast.LENGTH_SHORT).show();
	}
	
}
