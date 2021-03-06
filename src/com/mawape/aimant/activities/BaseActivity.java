package com.mawape.aimant.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.mawape.aimant.R;
import com.mawape.aimant.entities.Categoria;
import com.mawape.aimant.utilities.AndroidServicesUtil;
import com.mawape.aimant.utilities.ApacheStringUtils;

public abstract class BaseActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	@Override
	public void onStart() {
		super.onStart();
		// ... The rest of your onStart() code.
		EasyTracker.getInstance(this).activityStart(this); // Add this method.
	}

	@Override
	public void onStop() {
		super.onStop();
		// ... The rest of your onStop() code.
		EasyTracker.getInstance(this).activityStop(this); // Add this method.
	}

	protected void configureMenuBar(Categoria categoria, boolean canGoBack) {
		configureMenuBar(categoria, canGoBack, null);
	}

	protected void configureMenuBar(Categoria categoria, boolean canGoBack,
			final ArrayAdapter adapter) {
		this.configureMenuBar(categoria.getNombre(),
				Color.parseColor("#" + categoria.getColor()), canGoBack,
				adapter);
	}

	protected void configureMenuBar(String title, int color, boolean canGoBack,
			final ArrayAdapter adapter) {
		// goBack - title - information section
		TextView txtTitle = (TextView) findViewById(R.id.commonMenuTitle);
		txtTitle.setText(title);
		View view = findViewById(R.id.top_menu_bar);
		view.setBackgroundColor(color);

		if (canGoBack) {
			ImageView imgGoBack = (ImageView) findViewById(R.id.commonMenuBack);
			imgGoBack.setVisibility(View.VISIBLE);
			imgGoBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					onBackPressed();
				}
			});
		}

		configureInformationIcon();
		configureFilterAdapter(adapter);

	}

	protected void configureInformationIcon() {
		// handling menu info click
		ImageView infoImg = (ImageView) findViewById(R.id.commonMenuInformation);
		infoImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showSplashAimant();
			}
		});
	}

	protected void configureFilterAdapter(final ArrayAdapter adapter) {
		// search section
		// it may not contain a search field
		// finally it may not be an adapter with search capability
		if (adapter != null) {
			final EditText searchField = (EditText) findViewById(R.id.commonMenuSearchField);
			if (searchField != null) {
				searchField
						.setOnFocusChangeListener(new OnFocusChangeListener() {
							@Override
							public void onFocusChange(View v, boolean hasFocus) {
								String currentText = searchField.getText()
										.toString();
								if (hasFocus) {
									searchField.setText("");
								} else if (!hasFocus
										&& (currentText == null || currentText
												.length() == 0)) {
									searchField.setText(R.string.search_msg);
								}
							}
						});
			}

			// handling menu info click
			ImageView searchImg = (ImageView) findViewById(R.id.commonMenuSearchIcon);
			if (searchImg != null) {
				searchImg.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						searchField.requestFocus();
					}
				});
			}

			// configure filtering
			searchField.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					adapter.getFilter().filter(s); // Filter from my adapter
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	protected void filterList(String text) {
		if (!ApacheStringUtils.isEmpty(text)) {
			final EditText searchField = (EditText) findViewById(R.id.commonMenuSearchField);
			searchField.setText(text);
		}
	}

	protected void configureMenuBar() {
		configureMenuBar(null, false);
	}

	private void showSplashAimant() {
		Intent intent = new Intent(getApplicationContext(),
				InformationActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(intent);
	}

	protected void makePhoneCall(String phoneNumber) {
		AndroidServicesUtil.makePhoneCall(getApplicationContext(), phoneNumber);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	protected void openSecuritySettings() {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(intent);
	}
}
