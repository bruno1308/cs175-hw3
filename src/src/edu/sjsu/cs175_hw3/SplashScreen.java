package edu.sjsu.cs175_hw3;

import java.util.Random;

import edu.sjsu.cs175_hw3.R;

import android.support.v7.app.ActionBarActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SplashScreen extends ActionBarActivity {
	// Main screen
	public static final String PREFS_NAME = "MyPrefsFile";
	Button exercise;
	Button user;
	Button stats;

	Connection c = new Connection("54.173.5.18", 7890);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		// Start Client network thread
		try {
			c.execute("");
		} catch (Exception e) {

		}
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		exercise = (Button) findViewById(R.id.exercise);
		user = (Button) findViewById(R.id.user);
		stats = (Button) findViewById(R.id.stats);
		// Try to get first and last name
		String first_name = settings.getString("firstName", "None");
		String last_name = settings.getString("lastName", "None");

		if (first_name == "None" && last_name == "None") {
			// If they don't exist, block buttons
			stats.setEnabled(false);
			exercise.setEnabled(false);
			startUserMode(findViewById(android.R.id.content));
		} else {
			// If they do exist, display inspiration message
			while (!Connection.response.equals("Fingercise Server\n")) {
				//System.out.println("Flushing out");
			}
			// Get statistics to show inspiration message
			Connection.queue.add("statistics:" + first_name + " " + last_name);
			String response = "";
			response = Connection.response;
			while (Connection.sync == 0) {
				System.out.println("Sending request: " + response);
				response = Connection.response;
			}
			response = Connection.response;
			//System.out.println("Response is " + response);
			response="";
			
			String lines[] = null;
			
			while(lines == null || lines.length <5){
				
				 lines = Connection.response.split("\\r?\\n");
				 System.out.println(lines.length);
			}
			System.out.println(lines.length);
			// Got response from server
			//Connection.response = "";
			//response = Connection.response;

			// Split it into 3 lines
			//String lines[] = response.split("\\r?\\n");
			// Now split between values
			String[] fly = lines[1].split("\\t");
			String[] slider = lines[2].split("\\t");
			String[] typer = lines[3].split("\\t");
			Random r = new Random();
			int which = r.nextInt(3);
			String msg = "";
			/* Generates a random game to see how the user is doing at that game
			 Compares his best score with the overall best score and display
			 message */
			if (which == 0) {
				if (Double.parseDouble(fly[1]) > Double.parseDouble(fly[2])) {
					msg = "Let's go!! You have to improve on Fly!!";
				} else {
					msg = "You are doing well on Fly, keep playing it to improve even more!";
				}
			} else if (which == 1) {
				if (Double.parseDouble(slider[1]) > Double
						.parseDouble(slider[2])) {
					msg = "Slider is your weakness, practice it!!!";
				} else {
					msg = "Impressive results on Slider Master, but don't stop practicing!";
				}

			} else if (which == 2) {
				if (Double.parseDouble(typer[1]) > Double.parseDouble(typer[2])) {
					msg = "You're not doing well on Typer, but I believe on you!";
				} else {
					msg = "You are a Typer Master, but can you beat your own record?";
				}
			}
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG)
					.show();
			startExercise(findViewById(android.R.id.content));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
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

	// Whenever a fragment is shown, hide the others
	public void startExercise(View v) {
		operateFragments("UserMode", 0);
		operateFragments("Exercise", 1);
		operateFragments("Stats", 0);
	}

	public void startUserMode(View v) {
		operateFragments("UserMode", 1);
		operateFragments("Exercise", 0);
		operateFragments("Stats", 0);

	}

	public void startStats(View v) {
		operateFragments("Stats", 1);
		operateFragments("Exercise", 0);
		operateFragments("UserMode", 0);
	}

	// Switch between fragments hide/show
	public void operateFragments(String frag, int op) {
		FragmentManager manager = getFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		if (frag == "UserMode") {
			UserModeFragment um = (UserModeFragment) manager
					.findFragmentById(R.id.um_fragment);
			if (op == 0)
				transaction.hide(um);
			else {
				manager.beginTransaction()
						.setCustomAnimations(android.R.animator.fade_in,
								android.R.animator.fade_out).show(um).commit();
				transaction.show(um);
			}
		} else if (frag == "Exercise") {
			ExerciseFragment ef = (ExerciseFragment) manager
					.findFragmentById(R.id.frag_exercise);
			if (op == 0)
				transaction.hide(ef);
			else {
				manager.beginTransaction()
						.setCustomAnimations(android.R.animator.fade_in,
								android.R.animator.fade_out).show(ef).commit();
				transaction.show(ef);
			}
		} else if (frag == "Stats") {
			StatsFragment sf = (StatsFragment) manager
					.findFragmentById(R.id.frag_stats);
			if (op == 0)
				transaction.hide(sf);
			else {
				manager.beginTransaction()
						.setCustomAnimations(android.R.animator.fade_in,
								android.R.animator.fade_out).show(sf).commit();
				transaction.show(sf);
			}
		}
		transaction.commit();
	}
	
	

}
