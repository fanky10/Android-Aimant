package com.mawape.aimant.activities;

import android.content.Intent;

import com.mawape.aimant.R;

public class SplashMawapeActivity extends SplashActivity {
	private final int DISPLAY_LENGHT = 1000;// just one second

	@Override
	protected int getDefaultDisplayLength() {
		return DISPLAY_LENGHT;
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.splash_intro);
	}

	@Override
	protected Intent createIntent() {
		return new Intent(SplashMawapeActivity.this, SplashAimantActivity.class);
	}

}
