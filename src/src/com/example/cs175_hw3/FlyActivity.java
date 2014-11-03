package com.example.cs175_hw3;

import java.util.Date;
import java.util.Random;
import java.util.prefs.Preferences;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FlyActivity extends ActionBarActivity {
	Button buttons[] = new Button[12];
	public static final String PREFS_NAME = "MyPrefsFile";
	TextView txtNumber;
	TextView txtAvg;
	int count = 0;
	long avg = 0;
	long total=0L;
	long current;
	int numbers[];

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fly);

		txtNumber = (TextView) findViewById(R.id.txtNumber);
		txtAvg = (TextView) findViewById(R.id.txtAvg);
		initializeButtons();
		numbers = new int[12];
		Random r = new Random();
		// Generate random numbers and set a first one in the text view
		for (int i = 0; i < 12; ++i) {
			numbers[i] = r.nextInt(12);
			numbers[i]++;
		}
		txtNumber.setText(Integer.toString(numbers[count]));
		// Get current time
		current = new Date(System.currentTimeMillis()).getTime();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fly, menu);
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

	public void ButtonOnClick(View v) {
		// Switch to find which button was clicked
		switch (v.getId()) {
		case R.id.button1:
			handleClick(1);
			break;
		case R.id.button2:
			handleClick(2);
			break;

		case R.id.button3:
			handleClick(3);
			break;

		case R.id.button4:
			handleClick(4);
			break;

		case R.id.button5:
			handleClick(5);
			break;
		case R.id.button6:
			handleClick(6);
			break;

		case R.id.button7:
			handleClick(7);
			break;
		case R.id.button8:
			handleClick(8);
			break;

		case R.id.button9:
			handleClick(9);
			break;
		case R.id.button10:
			handleClick(10);
			break;

		case R.id.button11:
			handleClick(11);
			break;
		case R.id.button12:
			handleClick(12);
			break;
		}

	}

	public void initializeButtons() {
		// Initialize array of buttons
		buttons[0] = (Button) findViewById(R.id.button1);
		buttons[1] = (Button) findViewById(R.id.button2);
		buttons[2] = (Button) findViewById(R.id.button3);
		buttons[3] = (Button) findViewById(R.id.button4);
		buttons[4] = (Button) findViewById(R.id.button5);
		buttons[5] = (Button) findViewById(R.id.button6);
		buttons[6] = (Button) findViewById(R.id.button7);
		buttons[7] = (Button) findViewById(R.id.button8);
		buttons[8] = (Button) findViewById(R.id.button9);
		buttons[9] = (Button) findViewById(R.id.button10);
		buttons[10] = (Button) findViewById(R.id.button11);
		buttons[11] = (Button) findViewById(R.id.button12);
	}

	public void handleClick(int idBtn) {

		if (idBtn == numbers[count]) {
			// If randomized number matches the button that was clicked
			
			long finished = new Date(System.currentTimeMillis()).getTime();

			count++;
			if (count == 12) {
				finishGame();
				return;
			}
			//If first time clicked, don't divide by anything
			//Calcule new average speed
			
			total += (finished - current);

			avg = total / count;
			
			current = new Date(System.currentTimeMillis()).getTime();
			
			txtAvg.setText("Average Speed: " + Long.toString(avg) + "ms");
			txtNumber.setText(Integer.toString(numbers[count]));

		} else {
			// wrong button
		}
	}

	public void finishGame() {
		// Game is over, display info and save it on server
		String text = "Game is over, your average time was: "
				+ Long.toString(avg) + "ms";
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		//Get name stored and add message to Server's queue
		String msg = "result:";
		msg += settings.getString("firstName", "null");
		msg += " ";
		msg += settings.getString("lastName", "null");
		msg += "\tfly";
		msg += "\t" + Long.toString(avg);
		Connection.queue.add(msg);

		finish();
	}
}
