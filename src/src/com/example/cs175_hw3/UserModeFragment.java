package com.example.cs175_hw3;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class UserModeFragment extends Fragment {
	public static final String PREFS_NAME = "MyPrefsFile";
		EditText first;
		EditText last;
		Button ok;
		
		  @Override
		  public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
			   
		    View view = inflater.inflate(R.layout.fragment_user_mode,
		        container, false);
		    first = (EditText)view.findViewById(R.id.txtFirstName);
		    last = (EditText)view.findViewById(R.id.txtLastName);
		    ok = (Button)view.findViewById(R.id.btnOK);

		    ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
				      SharedPreferences.Editor editor = settings.edit();
				      String first_name =first.getText().toString();
				      String last_name = last.getText().toString();
				      editor.putString("firstName",first_name );
				      editor.putString("lastName",last_name );

				      // Commit the edits!
				      editor.commit();
				      Connection c = new Connection("10.1.10.169", 7890);
				      c.sendMessage("register:"+first_name +" "+last_name);
					
				}
			});
		    
		    return view;
		  }
		  

		  public void btnCancel(View v){
			  
		  }

		
}
