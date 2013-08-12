package com.mawape.aimant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
		if (savedInstanceState != null) {
			this.hasAutoClose = (Boolean) savedInstanceState.getBoolean(
					AppConstants.SPLASH_AIMANT_HAS_AUTOCLOSE_KEY, true);
		}
		init();
	}

	private void init() {
		// handling menu info click
		ImageView infoImg = (ImageView) findViewById(R.id.splashAboutClose);
		infoImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
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
	protected void postDelayed() {
		// TODO Auto-generated method stub
		if (hasAutoClose) {
			new Handler().postDelayed(this, getDisplayLength());
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}