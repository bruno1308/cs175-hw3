package com.example.cs175_hw3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;


import android.os.AsyncTask;
import android.util.Log;

public class Connection extends AsyncTask<String, Void, String> {

	public final String IP;
	public final int PORT;
	String response="";
	String question;
	Connection(String addr, int port){
		   this.IP = addr;
		   this.PORT = port;
	}
	@Override
	protected String doInBackground(String... arg0) {
		
		 Socket socket = null;
	      BufferedWriter writer = null;
	      BufferedReader reader =null;
	      BufferedReader reader2 =null;

	      try{
	         socket = new Socket(IP, PORT);
	         reader = new BufferedReader(
	            new InputStreamReader(socket.getInputStream()));

	         writer = new BufferedWriter(
	            new OutputStreamWriter(socket.getOutputStream()));
	         
	        	Log.i("Server", reader.readLine());
		        
	         String s = arg0[0] + "\n";
	         writer.write(s);
	         writer.flush();
	         reader2 = new BufferedReader(
	 	            new InputStreamReader(socket.getInputStream()));
	         String oneline;
	        response+=reader2.readLine();
	        Log.i("Server", response);
	         
	         return response;
	      }
	      catch (UnknownHostException e) {
	    	    // TODO Auto-generated catch block
	    	    e.printStackTrace();
	    	    response = "UnknownHostException: " + e.toString();
	    	   } catch (IOException e) {
	    	    // TODO Auto-generated catch block
	    	    e.printStackTrace();
	    	    response = "IOException: " + e.toString();
	    	   }finally{
	    	    if(socket != null){
	    	     try {
	    	      socket.close();
	    	     } catch (IOException e) {
	    	      // TODO Auto-generated catch block
	    	      e.printStackTrace();
	    	     }
	    	    }
	    	   }
	    	   return null;

	}
	@Override
  protected void onPostExecute(String result) {
   
  }
	
	public void sendMessage(String msg){
			this.execute(msg);

	}

}
