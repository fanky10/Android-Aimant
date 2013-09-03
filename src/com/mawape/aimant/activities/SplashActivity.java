package com.mawape.aimant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;

import com.mawape.aimant.R;

public abstract class SplashActivity extends BaseActivity implements Runnable {

	protected int displayLength = 1;
	protected Handler delayHandler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.delayHandler = new Handler();
		displayLength = getResources().getInteger(
				R.integer.default_splash_display_length);
		setContentView();
		postDelayed();
		addClickeableContainer();
	}

	private void addClickeableContainer() {
		View container = getContainer();
		container.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	protected void postDelayed() {
		this.delayHandler.postDelayed(this, getDisplayLength());
	}

	public void run() {
		startActivity(createIntent());
		finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.delayHandler.removeCallbacks(this);
		this.run();
	}

	protected int getDisplayLength() {
		return displayLength;
	}

	protected abstract void setContentView();

	protected abstract Intent createIntent();

	protected abstract View getContainer();

}
