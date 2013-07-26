package com.mawape.aimant.activities;

import android.content.Intent;

import com.mawape.aimant.R;

public class SplashAimantActivity extends SplashActivity {
	private final int DISPLAY_LENGHT = 2000;// just two seconds

	@Override
	protected int getDefaultDisplayLength() {
		return DISPLAY_LENGHT;
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.splash_aimant);
	}

	@Override
	protected Intent createIntent() {
		return new Intent(SplashAimantActivity.this, CategoriasAcivity.class);
	}

}