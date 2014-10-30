package com.example.cs175_hw3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.support.v7.app.ActionBarActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	Button socketButton;
	TextView socketOutput;
	EditText ipAddress;
	EditText port;
	EditText socketInput;
	public void onCreate(Bundle savedInstanceState) 
	   {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        socketButton = (Button)findViewById(R.id.button1);
	        ipAddress = (EditText)findViewById(R.id.ip);
	        port = (EditText)findViewById(R.id.port);
	        socketInput = (EditText)findViewById(R.id.input);
	        socketOutput = (TextView)findViewById(R.id.output);
	 
	        socketButton.setOnClickListener(new OnClickListener() {
	        public void onClick(final View v) {
	           socketOutput.setText("");
	           MyClientTask myClientTask = new MyClientTask(
	        		   ipAddress.getText().toString(),
	        	       Integer.parseInt(port.getText().toString()), socketInput.getText().toString());
	        	     myClientTask.execute();

	        } 

	       });
	   }
	public class MyClientTask extends AsyncTask<Void, Void, Void> {
		String ip;
		int port;
		String response;
		String msg;
		 MyClientTask(String addr, int port, String msg){
			   ip = addr;
			   this.port = port;
			   this.msg = msg;
			   
			  }
		@Override
		protected Void doInBackground(Void... params) {
			 Socket socket = null;
		      BufferedWriter writer = null;
		      BufferedReader reader =null;

		      try{
		         socket = new Socket(ip, port);
		         reader = new BufferedReader(
		            new InputStreamReader(socket.getInputStream()));

		         writer = new BufferedWriter(
		            new OutputStreamWriter(socket.getOutputStream()));
		         String s = msg + "\n";
		         writer.write(s);
		         writer.flush();
		         response = reader.readLine();       	  
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
  	  protected void onPostExecute(Void result) {
  	   socketOutput.setText(response);
  	   System.out.println("My response: " + response);
  	   super.onPostExecute(result);
  	  }
		

	    	  
	    	  
}

	   }

