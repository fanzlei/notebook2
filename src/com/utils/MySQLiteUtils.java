package com.utils;

import com.notebook2.AddActivity;
import com.notebook2.Main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MySQLiteUtils {

	SQLiteDatabase db;
	MySQLiteHelper helper;
	Context context;
	String name;
	public MySQLiteUtils(Context context) {
		// TODO Auto-generated constructor stub
		//���˲������ݿ��context
		System.out.println("mySQLiteUtils���캯��");
		helper=new MySQLiteHelper(context, "user_notebook", null,4);
		this.context=context;
	}

	

	public boolean saveNote(Note note){
		try {
			db=helper.getReadableDatabase();
			String sql="insert into user_notebook(name,title,content,date,type) values(?,?,?,?,?)";
			db.execSQL(sql, new String[]{note.getUser_name(),note.getTitle(),
					note.getContent(),note.getDate(),String.valueOf(note.getType())});
			System.out.println("�ʼǱ�����SQLite�гɹ�\n�ʼ�����Ϊ��");
			System.out.println("�ʼǴ�������Ϊ��"+note.getUser_name()+'\n'+note.getDate()+'\n'+note.getTitle()+'\n'+note.getContent());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public void saveNoteServerID(int serverID){
		db=helper.getReadableDatabase();
		Cursor cursor=db.rawQuery("select * from user_notebook", null);
		cursor.moveToLast();
		int id=cursor.getInt(0);
		String sql="update user_notebook set serverid='"+serverID+"' where id='"+id+"'";
		db.execSQL(sql);
		System.out.println("�ɹ�����ServerID");
	}
	public Note getNoteAt(int type,int position){
		db=helper.getReadableDatabase();
			System.out.println("type="+type);
			System.out.println("position="+position);
			if(type!=0){
				String thisType=String.valueOf(type);
				name=context.getSharedPreferences("localSave",context.MODE_WORLD_READABLE).getString("name", "");
				Cursor cursor=db.rawQuery("select * from user_notebook where name='"+name+"' and type='"+thisType+"'", null);
				//list��position��0��ʼ��cursor�Ľ��������1��ʼ cursor��ָ���0��ʼ
				int positionInSQLite=cursor.getCount()-position-1;
				System.out.println("positionInSQLite="+positionInSQLite);
				cursor.moveToPosition(positionInSQLite);
				Note note=new Note();
				note.setContent(cursor.getString(4));
				note.setTitle(cursor.getString(3));
				note.setType(cursor.getShort(6));
				note.setDate(cursor.getString(5));
				note.setId(cursor.getInt(0));
				return note;
			}else{
				String thisType=String.valueOf(type);
				name=context.getSharedPreferences("localSave",context.MODE_WORLD_READABLE).getString("name", "");
				Cursor cursor=db.rawQuery("select * from user_notebook where name='"+name+"'", null);
				//list��position��0��ʼ��cursor�Ľ��������1��ʼ cursor��ָ���0��ʼ
				int positionInSQLite=cursor.getCount()-position-1;
				System.out.println("positionInSQLite="+positionInSQLite);
				cursor.moveToPosition(positionInSQLite);
				Note note=new Note();
				note.setContent(cursor.getString(4));
				note.setTitle(cursor.getString(3));
				note.setType(cursor.getShort(6));
				note.setDate(cursor.getString(5));
				note.setId(cursor.getInt(0));
				return note;
				
			}
			
		}



	public void updateNote(Note note) {
		// TODO Auto-generated method stub
		int id=note.getId();
		String title=note.getTitle();
		String content=note.getContent();
		System.out.println("�޸ĺ��noteΪ��id="+id+"\ttitle="+title+"\tcontent="+content);
		String sql="update user_notebook set title='"+title+"',content='"+content+"' where id='"+note.getId()+"'";
		db=helper.getReadableDatabase();
		db.execSQL(sql);
		System.out.println("����note�ɹ�");
		Intent intent=new Intent(context,Main.class);
		context.startActivity(intent);
	}
		
	
}
