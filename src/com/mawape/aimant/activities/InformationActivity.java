package com.mawape.aimant.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
		RelativeLayout infoEmailLayout = (RelativeLayout) findViewById(R.id.infoEmailLayout);
		infoEmailLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendEmail();
			}
		});
		RelativeLayout infoTelefonoLayout = (RelativeLayout) findViewById(R.id.infoTelefonoLayout);
		infoTelefonoLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callAimant();
			}
		});
	}
	
	private void sendEmail(){
		String email = getString(R.string.info_email);
		String subject = getString(R.string.info_subject);
		String errorMessage = getString(R.string.no_email_client);
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
		i.putExtra(Intent.EXTRA_SUBJECT, subject);
		i.putExtra(Intent.EXTRA_TEXT   , "");
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(InformationActivity.this,errorMessage, Toast.LENGTH_SHORT).show();
		}
	}
	
	private void callAimant(){
		String phoneNumber = getString(R.string.info_telefono);
		Intent intent = new Intent(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		startActivity(intent);
	}
}
