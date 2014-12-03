package com.utils;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MySQLiteUtils {

	SQLiteDatabase db;
	MySQLiteHelper helper;
	Context context;
	String name;
	public MySQLiteUtils(Context context) {
		// TODO Auto-generated constructor stub
		//传人操作数据库的context
		System.out.println("mySQLiteUtils构造函数");
		helper=new MySQLiteHelper(context, "user_notebook", null,3);
		this.context=context;
	}

	public boolean saveNote(Note note){
		try {
			db=helper.getReadableDatabase();
			String sql="insert into user_notebook(name,title,content,date,type) values(?,?,?,?,?)";
			db.execSQL(sql, new String[]{note.getUser_name(),note.getTitle(),
					note.getContent(),note.getDate(),String.valueOf(note.getType())});
			System.out.println("笔记保存在SQLite中成功");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
