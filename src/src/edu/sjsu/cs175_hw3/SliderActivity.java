package edu.sjsu.cs175_hw3;

import java.util.Date;
import java.util.Random;

import edu.sjsu.cs175_hw3.R;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SliderActivity extends ActionBarActivity {
	public static final String PREFS_NAME = "MyPrefsFile";
	TextView txtNumber;
	TextView txtDisplay;
	TextView txtAvg;
	SeekBar seek;
	int count = 0;
	long current = 0;
	long total=0L;
	long avg;
	int[] numbers = new int[10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Slider Master game
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slider);

		txtNumber = (TextView) findViewById(R.id.txtNumberSlider);
		txtAvg = (TextView) findViewById(R.id.txtAvgSlider);
		txtDisplay = (TextView) findViewById(R.id.txtDisplay);
		seek = (SeekBar) findViewById(R.id.skbGame);
		Random r = new Random();
		// Generate random numbers
		for (int i = 0; i < 10; ++i) {
			numbers[i] = r.nextInt(100);
			// numbers[i]++;
		}
		txtNumber.setText(Integer.toString(numbers[count]));
		current = new Date(System.currentTimeMillis()).getTime();
		seek.setMax(100);
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// Show progress to user
				txtDisplay.setText(Integer.toString(progress));
				// If number randomized matches the progress
				if (progress == Integer
						.parseInt(txtNumber.getText().toString())) {
					long finished = new Date(System.currentTimeMillis())
							.getTime();
					count++;
					// Game is over
					if (count >= 10) {
						finishGame();
						return;
					}
					// If game is not over yet, calcule new avg and show it
					total += (finished - current);
					
					avg = total / count;
					current = new Date(System.currentTimeMillis()).getTime();
					txtAvg.setText("Average Speed: " + Long.toString(avg)
							+ "ms");
					if(count <10)
					txtNumber.setText(Integer.toString(numbers[count]));

				} else {
					// wrong slide
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.slider, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void finishGame() {
		// Game is over, show toast and save info on server
		String text = "Game is over, your average time was: "
				+ Long.toString(avg) + "ms";
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		// Get name and send request to Server's queue
		String msg = "result:";
		msg += settings.getString("firstName", "null");
		msg += " ";
		msg += settings.getString("lastName", "null");
		msg += "\tslider";
		msg += "\t" + Long.toString(avg);
		Connection.queue.add(msg);
		finish();
	}
}
