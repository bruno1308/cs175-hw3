package com.example.cs175_hw3;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ExerciseFragment extends Fragment {
	public static final String PREFS_NAME = "MyPrefsFile";
		Button fly;
		Button slider;
		
		  @Override
		  public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		    View view = inflater.inflate(R.layout.fragment_exercise,
		        container, false);
		    fly = (Button)view.findViewById(R.id.btnFly);
		    slider = (Button)view.findViewById(R.id.btnSlider);
		    fly.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent fly = new Intent(getActivity(), FlyActivity.class);
			    	startActivity(fly);
					
				}
			});
		    slider.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					/* */
					
				}
			});
		    
		    return view;
		  }
		  
		
}
