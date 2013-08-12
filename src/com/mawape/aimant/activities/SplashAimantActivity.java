package com.mawape.aimant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.mawape.aimant.R;
import com.mawape.aimant.constants.AppConstants;

public class SplashAimantActivity extends SplashActivity {

	private boolean hasAutoClose = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		displayLength = this.getResources().getInteger(
				R.integer.aimant_display_length);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			this.hasAutoClose = (Boolean) extras.getBoolean(
					AppConstants.SPLASH_AIMANT_HAS_AUTOCLOSE_KEY, true);
		}
		init();
	}

	private void init() {
		ImageView imgClose = (ImageView) findViewById(R.id.splashAboutClose);
		if (hasAutoClose) {
			imgClose.setVisibility(View.GONE);
			postDelayed();
		} else {
			imgClose.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		}
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

}