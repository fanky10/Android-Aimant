package com.mawape.aimant.activities;


import com.mawape.aimant.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public abstract class SplashActivity extends Activity implements Runnable {

	protected int displayLength = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		displayLength = getResources().getInteger(R.integer.default_splash_display_length);
		setContentView();
		postDelayed();
	}

	protected abstract void postDelayed();

	public void run() {
		startActivity(createIntent());
		finish();
	}

	protected int getDisplayLength() {
		return displayLength;
	}

	protected abstract void setContentView();

	protected abstract Intent createIntent();

}
