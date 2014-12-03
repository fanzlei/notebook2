package com.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.utils.JsonUtils;
import com.utils.Note;

public class SaveToServer extends Thread{

	Note note;
	public SaveToServer(Note note) {
		// TODO Auto-generated constructor stub
		this.note=note;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		String jsonString =note.toJsonString();
		OutputStream os;
		HttpURLConnection conn;
		URL url;
		try {
			url = new URL("http://192.168.0.108:8080/Notebook2_service/CreateNote");
			conn=(HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setReadTimeout(5000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			os=conn.getOutputStream();
			os.write(jsonString.getBytes());
			os.flush();
			/*PrintWriter out=new PrintWriter(conn.getOutputStream());
			out.print(jsonString);*/
			System.out.println("已发送json到服务器");
			if(conn.getResponseCode()==200){
				
			}
			//os.close();
			//conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("服务器连接失败");
		}
		
		
	
		
	}

	public boolean isSaveToServer(int id){
		
		return false;
	}
	public void save(){
		this.start();
		
	}
}
