package com.mawape.aimant.activities;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.mawape.aimant.R;

public class InformationActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.informacion_layout);
		init();
	}

	private void init() {
		ImageView imgClose = (ImageView) findViewById(R.id.infoClose);
		imgClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}
}
