package com.example.cs175_hw3;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StatsFragment extends Fragment {
	Button request;
	EditText name;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
			   
		    View view = inflater.inflate(R.layout.fragment_stats,
		        container, false);
		    name = (EditText)view.findViewById(R.id.txtFindUser);
		    request = (Button)view.findViewById(R.id.btnStats);
		    request.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Connection c = new Connection("10.1.10.169",7890);
					  c.sendMessage("statistics:"+name.getText().toString());
					/*String lines[] = result.split("\\r?\\n");
					for(String line: lines){
						String values[] = line.split("\\t");
					}*/
					
				}
			});
		    
		    return view;
	}
}
