package com.utils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.notebook2.AddActivity;
import com.notebook2.Main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
		//传人操作数据库的context
		System.out.println("mySQLiteUtils构造函数");
		helper=new MySQLiteHelper(context, "user_notebook", null,4);
		this.context=context;
	}

	

	public boolean saveNote(Note note){
		try {
			db=helper.getReadableDatabase();
			String sql="insert into user_notebook(name,title,content,date,type,serverid) values(?,?,?,?,?,?)";
			db.execSQL(sql, new String[]{note.getUser_name(),note.getTitle(),
					note.getContent(),note.getDate(),String.valueOf(note.getType()),note.getServerId()});
			System.out.println("笔记保存在SQLite中成功\n笔记内容为：");
			System.out.println("笔记创建内容为："+note.getUser_name()+'\n'+note.getDate()+'\n'+note.getTitle()+'\n'+note.getContent());
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public void saveNoteServerID(Note note,int serverID){
		//note的id是数据库自动创建的，现在这个note只带数据，没有id
		db=helper.getReadableDatabase();
		
		Cursor cursor= db.rawQuery("select id from user_notebook where date='"+note.getDate()+"'", null);
		cursor.moveToFirst();
		int id=cursor.getInt(0);
		System.out.println("SQLite中该note的id为："+id);
		String sql="update user_notebook set serverid='"+serverID+"' where id='"+id+"'";
		db.execSQL(sql);
		System.out.println("保存并获得serverid，该note为：\nid=SQLite自动创建"+"\nname="+note.getUser_name()
				+"\ntitle="+note.getTitle()+"content="+note.getContent()+"\ndate="+note.getDate()
				+"\ntype="+note.getType()+"\nserverId="+note.getServerId());
	}
	public Note getNoteAt(int type,int position){
		db=helper.getReadableDatabase();
			System.out.println("type="+type);
			System.out.println("position="+position);
			if(type!=0){
				String thisType=String.valueOf(type);
				name=context.getSharedPreferences("localSave",context.MODE_WORLD_READABLE).getString("name", "");
				Cursor cursor=db.rawQuery("select * from user_notebook where name='"+name+"' and type='"+thisType+"'", null);
				//list中position从0开始，cursor的结果数量从1开始 cursor的指针从0开始
				int positionInSQLite=cursor.getCount()-position-1;
				System.out.println("positionInSQLite="+positionInSQLite);
				cursor.moveToPosition(positionInSQLite);
				Note note=new Note();
				note.setContent(cursor.getString(4));
				note.setTitle(cursor.getString(3));
				note.setType(cursor.getShort(6));
				note.setDate(cursor.getString(5));
				note.setId(cursor.getInt(0));
				note.setServerId(cursor.getString(1));
				return note;
			}else{
				String thisType=String.valueOf(type);
				name=context.getSharedPreferences("localSave",context.MODE_WORLD_READABLE).getString("name", "");
				Cursor cursor=db.rawQuery("select * from user_notebook where name='"+name+"'", null);
				//list中position从0开始，cursor的结果数量从1开始 cursor的指针从0开始
				int positionInSQLite=cursor.getCount()-position-1;
				System.out.println("positionInSQLite="+positionInSQLite);
				cursor.moveToPosition(positionInSQLite);
				Note note=new Note();
				note.setContent(cursor.getString(4));
				note.setTitle(cursor.getString(3));
				note.setType(cursor.getShort(6));
				note.setDate(cursor.getString(5));
				note.setId(cursor.getInt(0));
				note.setServerId(cursor.getString(1));
				return note;
				
			}
			
		}



	public void updateNote(Note note) {
		// TODO Auto-generated method stub
		int id=note.getId();
		String title=note.getTitle();
		String content=note.getContent();
		String date=new Date(System.currentTimeMillis()).toLocaleString();
		System.out.println("修改后的note为：id="+id+"\ttitle="+title+"\tcontent="+content);
		String sql="update user_notebook set title='"+title+"',date='"+date+"',content='"+content+"' where id='"+note.getId()+"'";
		db=helper.getReadableDatabase();
		db.execSQL(sql);
		System.out.println("更新note成功");
		Intent intent=new Intent(context,Main.class);
		context.startActivity(intent);
	}



	public void deleteNote(Note note) {
		// TODO Auto-generated method stub
		String sql="delete from user_notebook where id='"+note.getId()+"'";
		db=helper.getReadableDatabase();
		db.execSQL(sql);
		if(note.getServerId()!=null&&note.getServerId()!=""){
			//删除某个note的时候记录下这个note的serverId，同步的时候让服务器删除该note。无网络下删除note同步是也能删除该note
			SharedPreferences sp= context.getSharedPreferences("localSave", context.MODE_WORLD_READABLE);
			SharedPreferences.Editor editor=sp.edit();
			Set<String> set;
		    set= sp.getStringSet("deletedNote", new HashSet<String>());
		    set.add(note.getServerId());
		    editor.putStringSet("deletedNote", set);
		    editor.commit();
		    context.startActivity(new Intent(context,Main.class));
		    
		}
		
		System.out.println("删除note成功，该note的ID为："+note.getId()+"serverId为："+note.getServerId());
	}



	/*public List<Note> getAllNote() {
		// TODO Auto-generated method stub
		db=helper.getReadableDatabase();
		List<Note> list=new ArrayList<Note>();
		SharedPreferences sp=context.getSharedPreferences("localSave", context.MODE_WORLD_READABLE);
	    SharedPreferences.Editor editor=sp.edit();
		String name=context.getSharedPreferences("localSave", context.MODE_WORLD_READABLE).getString("name", "");
		String sql="select * from user_notebook where name='"+name+"'";
		Cursor cursor=db.rawQuery(sql, null);
		while(cursor.moveToNext()){
			Note note=new Note();
			note.setId(cursor.getInt(0));
			note.setServerId(cursor.getString(1));
			note.setUser_name(cursor.getString(2));
			note.setTitle(cursor.getString(3));
			note.setContent(cursor.getString(4));
			note.setDate(cursor.getString(5));
			note.setType(Integer.valueOf(cursor.getString(6)));
			list.add(note);
		}
		return list;
	}*/
		
	
}
