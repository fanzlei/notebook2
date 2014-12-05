package com.listener;

import com.notebook2.NoteDetail;
import com.utils.MySQLiteUtils;
import com.utils.Note;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
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
		
		
		return false;
	}

}
