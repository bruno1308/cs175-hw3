package edu.sjsu.cs175_hw3;

import edu.sjsu.cs175_hw3.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ExerciseFragment extends Fragment {
	public static final String PREFS_NAME = "MyPrefsFile";
	// Fragment to handle the 3 exercises
	Button fly;
	Button slider;
	Button typer;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_exercise, container,
				false);
		fly = (Button) view.findViewById(R.id.btnFly);
		slider = (Button) view.findViewById(R.id.btnSlider);
		typer = (Button) view.findViewById(R.id.btnTyper);
		// Listeners for all games, check if they are clicked
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
				Intent slider = new Intent(getActivity(), SliderActivity.class);
				startActivity(slider);

			}
		});
		typer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent typer = new Intent(getActivity(), TyperActivity.class);
				startActivity(typer);
			}
		});
		return view;
	}

}
