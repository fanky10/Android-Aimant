package com.mawape.aimant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mawape.aimant.R;

public class SplashAimantActivity extends SplashActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.splash_about);
	}

	@Override
	protected Intent createIntent() {
		return new Intent(SplashAimantActivity.this, CategoriasAcivity.class);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(this.getClass().getName(), "saved instance!");
	}

	@Override
	protected View getContainer() {
		return findViewById(R.id.splashAboutContainer);
	}

	@Override
	protected int getDisplayLength() {
		return getResources().getInteger(R.integer.aimant_display_length);
	}

}