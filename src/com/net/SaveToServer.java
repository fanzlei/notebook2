package com.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;

import com.utils.JsonUtils;
import com.utils.MySQLiteUtils;
import com.utils.Note;

public class SaveToServer extends Thread{

	Note note;
	Context context;
	public SaveToServer(Note note,Context context) {
		// TODO Auto-generated constructor stub
		this.note=note;
		this.context=context;
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
			url = new URL(URLText.urlText+"CreateNote");
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
			System.out.println("发送json服务器");
			
			if(conn.getResponseCode()==200){
				//����������������ID
			    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    String line;
			    String idString="";
			    while((line=br.readLine())!=null){
			    	idString+=line;
			    }
			    int id=Integer.valueOf(idString);
			    System.out.println("服务器返回noted的serverID为："+id);
			    new MySQLiteUtils(context).saveNoteServerID(note,id);
			    conn.disconnect();
			    os.close();
			}
			//os.close();
			//conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��������������");
		}
			
		
		
		
	
		
	}

	public boolean isSaveToServer(int id){
		
		return false;
	}
	
}
