package edu.sjsu.cs175_hw3;

import edu.sjsu.cs175_hw3.R;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserModeFragment extends Fragment {
	// Fragment that takes care of the User Modes
	public static final String PREFS_NAME = "MyPrefsFile";
	EditText first;
	EditText last;
	Button ok;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.fragment_user_mode,
				container, false);
		first = (EditText) view.findViewById(R.id.txtFirstName);
		last = (EditText) view.findViewById(R.id.txtLastName);
		ok = (Button) view.findViewById(R.id.btnOK);

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// After clicked button, close keyboard
				InputMethodManager inputManager = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);

				inputManager.hideSoftInputFromWindow(getActivity()
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				// Save preferences
				SharedPreferences settings = getActivity()
						.getSharedPreferences(PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				String first_name = first.getText().toString();
				String last_name = last.getText().toString();
				Button user = (Button) getActivity()
						.findViewById(R.id.exercise);
				Button stats = (Button) getActivity().findViewById(R.id.stats);

				// Tell the server that user was registered
				String msg = "register:" + first_name + " " + last_name;
				Connection.response = " ";
				Connection.queue.add(msg);
				while (Connection.sync == 0 || Connection.response.equals("")) {
					// Waiting for server's response
				}
				String response =Connection.response;
				while(!response.equals("Okay") && !response.equals("Sorry")){
					 response =Connection.response;
					response = response.replaceAll("[\\r\\n]","").trim();
				}
				if (response.equals("Sorry")) {
					// User duplicated
					response = "User already exists!";

				} else {
					// Add user to the server
					response = "Configurations updated!";
					editor.putString("firstName", first_name);
					editor.putString("lastName", last_name);
					editor.commit();
					// Enable buttons, if they were disabled
					user.setEnabled(true);
					stats.setEnabled(true);
				}
				Toast.makeText(view.getContext(), response, Toast.LENGTH_LONG)
						.show();

				first.setText("");
				last.setText("");

			}
		});

		return view;
	}

	public void btnCancel(View v) {

	}

}
