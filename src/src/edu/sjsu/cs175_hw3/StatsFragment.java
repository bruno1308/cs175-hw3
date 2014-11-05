package edu.sjsu.cs175_hw3;

import java.text.DecimalFormat;

import edu.sjsu.cs175_hw3.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StatsFragment extends Fragment {
	// Get statistics and show them
	Button request;
	EditText name;
	TextView[] results = new TextView[30];

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_stats, container, false);
		name = (EditText) view.findViewById(R.id.txtFindUser);
		request = (Button) view.findViewById(R.id.btnStats);

		initializeTextViews(view);
		request.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(name.getText().toString().equals("")){
					return;
				}
				InputMethodManager inputManager = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				// Hide keyboard
				inputManager.hideSoftInputFromWindow(getActivity()
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				// Send request to server's queue
				Connection.response = "";
				Connection.queue.add("statistics:" + name.getText().toString());
				String response = Connection.response;

				while (Connection.sync == 0 || Connection.response.equals("")) {
					//Wait for the server to answer
				}
				response = Connection.response;
				// Got response from server
				Connection.response = "";
				//System.out.println(response);
				// Split it into 3 lines
				String lines[] = response.split("\\r?\\n");
				// Now split between values
				String[] fly = lines[0].split("\\t");
				String[] slider = lines[1].split("\\t");
				String[] typer = lines[2].split("\\t");
				DecimalFormat df = new DecimalFormat("0.0#");
				for (int i = 0; i < fly.length; ++i) {
					if (fly[i].matches("[-+]?\\d*\\.?\\d+")) {
						// It's a number, display it in its TextView
						results[i - 1].setText(df.format(Double
								.parseDouble(fly[i])));
					}
				}
				for (int i = 0; i < slider.length; ++i) {
					if (slider[i].matches("[-+]?\\d*\\.?\\d+")) {
						// It's a number, display it in its TextView
						results[i + 5].setText(df.format(Double
								.parseDouble(slider[i])));
					}
				}
				for (int i = 0; i < typer.length; ++i) {
					if (typer[i].matches("[-+]?\\d*\\.?\\d+")
							|| typer[i].equals("-")) {
						// It's a number, display it in its TextView
						results[i + 11].setText(df.format(Double
								.parseDouble(typer[i])));
					}
				}
			}
		});

		return view;
	}

	public void initializeTextViews(View v) {
		// Initialize textviews
		results[0] = (TextView) v.findViewById(R.id.myHighFly);
		results[1] = (TextView) v.findViewById(R.id.oaHighFly);
		results[2] = (TextView) v.findViewById(R.id.oaAvgFly);
		results[3] = (TextView) v.findViewById(R.id.myAvgHourFly);
		results[4] = (TextView) v.findViewById(R.id.myAvgMonthFly);
		results[5] = (TextView) v.findViewById(R.id.myAvgWeekFly);
		results[6] = (TextView) v.findViewById(R.id.myHighSlider);
		results[7] = (TextView) v.findViewById(R.id.oaHighSlider);
		results[8] = (TextView) v.findViewById(R.id.oaAvgSlider);
		results[9] = (TextView) v.findViewById(R.id.myAvgHourSlider);
		results[10] = (TextView) v.findViewById(R.id.myAvgMonthSlider);
		results[11] = (TextView) v.findViewById(R.id.myAvgWeekSlider);
		results[12] = (TextView) v.findViewById(R.id.myHighSome);
		results[13] = (TextView) v.findViewById(R.id.oaHighSome);
		results[14] = (TextView) v.findViewById(R.id.oaAvgSome);
		results[15] = (TextView) v.findViewById(R.id.myAvgHourSome);
		results[16] = (TextView) v.findViewById(R.id.myAvgMonthSome);
		results[17] = (TextView) v.findViewById(R.id.myAvgWeekSome);
	}
}
