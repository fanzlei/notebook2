package com.notebook2;

import com.fanz.notebook2.R;
import com.utils.MySQLiteUtils;
import com.utils.Note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NoteDetail extends Activity{

	/*List列表中当前显示的笔记类型和笔记位置对应与数据库中相应类型note的位置
	 * 注意list列表中note的顺序和数据库中的note顺序不同
	 * 
	 * */
	EditText title,content;
	TextView date;
	int type;
	int position;
	Note note;
	public NoteDetail() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_detail);
		title=(EditText) findViewById(R.id.note_detail_title);
		date=(TextView) findViewById(R.id.note_detail_date);
		content=(EditText) findViewById(R.id.note_detail_content);
		Intent intent=this.getIntent();
		type=intent.getExtras().getInt("type");
		position=intent.getExtras().getInt("position");
		note=new MySQLiteUtils(this).getNoteAt(type, position);
		title.setText(note.getTitle());
		date.setText(note.getDate());
		content.setText(note.getContent());
		
	}

	public void clickButton(View v){
		switch(v.getId()){
		case R.id.note_detail_back:
			Intent intent=new Intent(this,Main.class);
			startActivity(intent);
			break;
		case R.id.note_detail_save:
			note.setTitle(title.getText().toString().trim());
			note.setContent(content.getText().toString());			
			new MySQLiteUtils(this).updateNote(note);
			break;
		}
	}
}
