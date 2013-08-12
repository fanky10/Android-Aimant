package com.mawape.aimant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mawape.aimant.R;

public class SplashMawapeActivity extends SplashActivity {
	
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.displayLength =this.getResources().getInteger(R.integer.mawape_display_length);
	}
	
	@Override
	protected void setContentView() {
		setContentView(R.layout.splash_intro);
	}

	@Override
	protected Intent createIntent() {
		return new Intent(SplashMawapeActivity.this, SplashAimantActivity.class);
	}

	@Override
	protected void postDelayed() {
		/*
		 * New Handler to start the Menu-Activity and close this Splash-Screen
		 * after some seconds.
		 */
		new Handler().postDelayed(this, getDisplayLength());
		
	}

}
