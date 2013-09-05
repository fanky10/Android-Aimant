package com.mawape.aimant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mawape.aimant.R;

public class SplashMawapeActivity extends SplashActivity {

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
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
	protected View getContainer() {
		return findViewById(R.id.splashIntroContainer);
	}

	@Override
	protected int getDisplayLength() {
		return getResources().getInteger(R.integer.mawape_display_length);
	}

}
