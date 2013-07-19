package com.mawape.aimant.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public abstract class SplashActivity extends Activity implements Runnable {
	private final int DEFAULT_DISPLAY_LENGHT = 5000;// five seconds

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView();

		/*
		 * New Handler to start the Menu-Activity and close this Splash-Screen
		 * after some seconds.
		 */
		new Handler().postDelayed(this, getDefaultDisplayLength());
	}

	public void run() {
		startActivity(createIntent());
		finish();
	}

	protected int getDefaultDisplayLength() {
		return DEFAULT_DISPLAY_LENGHT;
	}

	protected abstract void setContentView();

	protected abstract Intent createIntent();

}
