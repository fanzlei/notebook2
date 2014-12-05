package com.notebook2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.utils.Note;

public class NoteList {

	List<Note> myList=new ArrayList<Note>();
	List<Note> lifeList=new ArrayList<Note>();
	List<Note> workList=new ArrayList<Note>();
	List<Note> otherList=new ArrayList<Note>();
	Context context;
	Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			
		}
		
	};
	public NoteList(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		new LoadListThread(handler).start();
	}
	public class LoadListThread extends Thread{

		Handler handler;
		public LoadListThread(Handler handler){
			this.handler=handler;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			
		}
		 
	 }
}
