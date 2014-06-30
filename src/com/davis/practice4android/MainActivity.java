package com.davis.practice4android;

import com.davis.practice4android.ui.ProgressWheel;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	private ProgressWheel mProgressWheel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
//		mProgressWheel.setProgress(270);
		mProgressWheel.spin();
	}

	private void init() {
		mProgressWheel = (ProgressWheel)findViewById(R.id.progresswheel);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
