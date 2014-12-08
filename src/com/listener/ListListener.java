package com.listener;

import com.adapter.MyAdapter;
import com.fanz.notebook2.R;
import com.notebook2.Main;
import com.notebook2.NoteDetail;
import com.utils.MySQLiteHelper;
import com.utils.MySQLiteUtils;
import com.utils.Note;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class ListListener implements OnItemClickListener, OnItemLongClickListener {

	Context context;
	int nowShowList;
	MySQLiteUtils mySQLiteUtils;
	Note note;
	public ListListener(Context context,int nowShowList) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.nowShowList=nowShowList;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(context,NoteDetail.class);
		intent.putExtra("type", nowShowList);
		intent.putExtra("position", position);
		context.startActivity(intent);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		
		PopupMenu pop=new PopupMenu(context, view);
		pop.inflate(R.menu.note_long_click);
		pop.show();
		MySQLiteUtils sqlUtil=new MySQLiteUtils(context);
		final Note note= sqlUtil.getNoteAt(nowShowList, position);
		pop.setOnMenuItemClickListener(new OnMenuItemClickListener(){

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				// TODO Auto-generated method stub
				switch(arg0.getItemId()){
				case R.id.popupMenu_delete:
					new MySQLiteUtils(context).deleteNote(note);
					break;
				
				}
				
				return false;
			}});
		
		return true;
	}

}
