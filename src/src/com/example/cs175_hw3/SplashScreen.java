package com.example.cs175_hw3;

import android.support.v7.app.ActionBarActivity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SplashScreen extends ActionBarActivity {
	public static final String PREFS_NAME = "MyPrefsFile";
	Button exercise;
	Button user;
	Button stats;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		exercise = (Button)findViewById(R.id.exercise);
		user = (Button)findViewById(R.id.user);
		stats = (Button)findViewById(R.id.stats);
		
		String first_name = settings.getString("firstName", "None");
		String last_name = settings.getString("lastName", "None");
		
		if(first_name =="None" && last_name == "None"){
			stats.setEnabled(false);
			exercise.setEnabled(false);
			startUserMode(findViewById(android.R.id.content));
		}else{
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
	
    public void startExercise(View v){
    	operateFragments("UserMode",0);
    	operateFragments("Exercise", 1);
    	operateFragments("Stats",0);
    }
    public void startUserMode(View v){
    	operateFragments("UserMode",1);
    	operateFragments("Exercise", 0);
    	operateFragments("Stats",0);
    	
    }
    public void startStats(View v){
    	operateFragments("Stats",1);
    	operateFragments("Exercise", 0);
    	operateFragments("UserMode",0);    	
    }
    
    public void operateFragments(String frag, int op ){
    	FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(frag == "UserMode"){
        	UserModeFragment um = (UserModeFragment) manager.findFragmentById(R.id.um_fragment);
        	if(op==0)  	transaction.hide(um);
        	else {
        		manager.beginTransaction()
  	          .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
  	          .show(um)
  	          .commit();
        		transaction.show(um);
        		}
        }
        else if(frag == "Exercise"){
        	ExerciseFragment ef = (ExerciseFragment) manager.findFragmentById(R.id.frag_exercise);
        	if(op==0)  	transaction.hide(ef);
        	else {
        		manager.beginTransaction()
  	          .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
  	          .show(ef)
  	          .commit();
        		transaction.show(ef);
        		}
        }
        else if(frag == "Stats"){
        	StatsFragment sf = (StatsFragment) manager.findFragmentById(R.id.frag_stats);
        	if(op==0)  	transaction.hide(sf);
        	else {
        		manager.beginTransaction()
  	          .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
  	          .show(sf)
  	          .commit();
        		transaction.show(sf);
        		}
        }
        transaction.commit();
    }
}

