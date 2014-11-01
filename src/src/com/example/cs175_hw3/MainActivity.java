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
	           

	        } 

	       });
	   }
}


