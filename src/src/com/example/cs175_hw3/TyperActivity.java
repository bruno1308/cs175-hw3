package com.example.cs175_hw3;

import java.util.Date;
import java.util.Random;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TyperActivity extends ActionBarActivity {
	// Game Typer Master
	public static final String PREFS_NAME = "MyPrefsFile";
	EditText txtTyper;
	TextView txtWord;
	Long time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_typer);
		txtTyper = (EditText) findViewById(R.id.txtTyper);
		txtWord = (TextView) findViewById(R.id.txtWord);
		final Long current = new Date(System.currentTimeMillis()).getTime();
		txtTyper.addTextChangedListener(new TextWatcher() {
			// Whenever Text changes, check if it matches
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// Check if the words match
				if (txtTyper.getText().toString()
						.equals(txtWord.getText().toString())) {
					// Text match, finish game
					Long end = new Date(System.currentTimeMillis()).getTime();
					time = end - current;
					finishGame();
				}
			}

			@Override
			public void afterTextChanged(Editable arg0) {

			}
		});
		// Build word that will be displayed and show it
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		txtWord.setText(output);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.typer, menu);
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
		// Game is over, display toast and save info in the server
		String text = "Game is over, your time was: " + Long.toString(time)
				+ "ms";
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		// Get name and add the result to the server
		String msg = "result:";
		msg += settings.getString("firstName", "null");
		msg += " ";
		msg += settings.getString("lastName", "null");
		msg += "\ttyper";
		msg += "\t" + Long.toString(time);
		Connection.queue.add(msg);

		finish();
	}
}
