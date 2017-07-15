package com.camera;

import java.util.Timer;
import java.util.TimerTask;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;


import java.util.Timer;
import java.util.TimerTask;

import com.necatievren.camera.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


public class SplashActivity extends Activity{
	private long splashDelay = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		TimerTask tTask = new TimerTask() {
			@Override
		    public void run() {
				Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
		        startActivity(mainIntent);

		        finish();
		    }
		};


		Timer timer = new Timer();

		timer.schedule(tTask, splashDelay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

}
