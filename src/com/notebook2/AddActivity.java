package com.notebook2;

import com.fanz.notebook2.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;

public class AddActivity extends Activity{

	EditText content;
	boolean hasContent=false;
	Context context;
	final int RESULT_LOAD_IMAGE=13322;
	final int REQUEST_CODE_CAMERA=23414;
	public AddActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_activity);
		context=this;
		content=(EditText) findViewById(R.id.note_content_edit);
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==RESULT_LOAD_IMAGE && 
				resultCode==RESULT_OK     &&
				data.getData()!=null){
			Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
  
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
  
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap=BitmapFactory.decodeFile(picturePath);
            
            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.note_content_ll);
            ImageView imageView=new ImageView(this);
            imageView.setImageBitmap(bitmap);
            
            linearLayout.addView(imageView);
		}
		if(requestCode==REQUEST_CODE_CAMERA &&
				resultCode==RESULT_OK &&
				data!=null){
			Bundle extras = data.getExtras();    
            Bitmap bitmap = (Bitmap) extras.get("data");  
            
            
            LinearLayout linearLayout=(LinearLayout)findViewById(R.id.note_content_ll);
            ImageView imageView=new ImageView(this);
            imageView.setImageBitmap(bitmap);
            linearLayout.addView(imageView);
		}
	}

	/**
	 * @param v
	 */
	public void add_picture(View v){
/*		PopupWindow pw=new PopupWindow();
		View p= this.getLayoutInflater().inflate(R.layout.popup_picture_selector, null);
		pw.setContentView(p);
		System.out.println("showMenu");
		pw.showAtLocation(v, Gravity.START, -56, -200);*/
		
		PopupMenu pm=new PopupMenu(this, v);
		pm.getMenuInflater().inflate(R.menu.picture, pm.getMenu());
		pm.setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				if(arg0.getItemId()==R.id.picture_libs){
					System.out.println("打开图库");
					openPictureLibrary();
					return true;
				}else{
					System.out.println("打开相机");
					openCamera();
					return true;
				}
			}});
		pm.show();
		
	}
    protected void openCamera() {
		// TODO Auto-generated method stub
    	Intent i = new Intent("android.media.action.IMAGE_CAPTURE");    
        startActivityForResult(i, REQUEST_CODE_CAMERA);
	}

	protected void openPictureLibrary() {
		// TODO Auto-generated method stub
    	Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); 
    	startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	public void add_sound(View v){
		System.out.println("获取录音");
	}
    public void add_save(View v){
		System.out.println("保存记录");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(!content.getText().toString().trim().equals("")){
				new AlertDialog.Builder(AddActivity.this)
				.setTitle("是否保存？")
				.setNegativeButton("取消", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent=new Intent(AddActivity.this,Main.class);
						startActivity(intent);
					}})
				.setPositiveButton("保存", new OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						saveRecord();
					}}).show();
			}
			
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void saveRecord() {
		// TODO Auto-generated method stub
		
	}
    
}
