package com.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper{

	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	String CREATE_TABLE="create table user_notebook(id integer primary key autoincrement,serverid integer,name,title,content,date,type);";
	String UPDATE_TABLE="alter table user_notebook add column user_notebookcol integer NULL";
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		//��һ��ʹ�����ݿ�ʱ�Զ�����
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL(UPDATE_TABLE);
		System.out.println("�޸����ݿ�ṹ�ɹ�");
	}

}
