package com.utils;

import java.io.InputStream;
import java.sql.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;



public class Note {

	String user_name;
	String pass;
	String title;
	String content;
	String date;
	int id;
	JSONObject jsonObject;
	int type=0;
	String serverId="";
	public Note(InputStream is) {
		// TODO Auto-generated constructor stub
		try {
			String jsonString=new JsonUtils().inputStreamToString(is);
			jsonObject=new JSONObject(jsonString);
			user_name=jsonObject.getString("user_name");
			pass=jsonObject.getString("pass");
			title=jsonObject.getString("title");
			content=jsonObject.getString("content");
			date=new Date(System.currentTimeMillis()).toLocaleString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Note(String user_name,String pass,String title,String content){
		this.user_name=user_name;
		this.pass=pass;
		this.title=title;
		this.content=content;
		this.date=new Date(System.currentTimeMillis()).toLocaleString();
	}
	public Note(){
		this.date=new Date(System.currentTimeMillis()).toLocaleString();
	}
	public String getUser_name() {
		return user_name;
	}
	
	public String getPass() {
		return pass;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getDate() {
		return date;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setUser_name(String user_name){
		this.user_name=user_name;
	}
	public void setType(int type){
		this.type=type;
	}
	public int getType(){
		return type;
	}
	public String toJsonString(){
		JSONObject jsonObject=new JSONObject();
		try {
			jsonObject.put("user_name", user_name);
			jsonObject.put("title", title);
			jsonObject.put("content",content);
			jsonObject.put("date", date);
			jsonObject.put("type", type);
			jsonObject.put("pass", pass);
			return jsonObject.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
	}
	public void setPass(String pass) {
		// TODO Auto-generated method stub
		this.pass=pass;
	}
	public void setDate(String date){
		this.date=date;
	}
	public void setId(int id){
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public void setServerId(String serverId){
		this.serverId=serverId;
	}
	public String getServerId(){
		return serverId;
	}
}
