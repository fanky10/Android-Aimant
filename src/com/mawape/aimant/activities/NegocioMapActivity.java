package com.mawape.aimant.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.mawape.aimant.R;

public class NegocioMapActivity extends FragmentActivity implements
		LocationListener {
	private LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		init();
	}

	private void init() {
		boolean isGpsEnabled = false, isNetworkEnabled = false;
		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		try {
			isGpsEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
			Log.d(NegocioMapActivity.class.getName(),
					"gps provider exception: " + ex.getMessage());
		}
		try {
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
			Log.d(NegocioMapActivity.class.getName(),
					"network provider exception: " + ex.getMessage());
		}
		if (!isGpsEnabled && !isNetworkEnabled) {
			showLocationSettingsDialog();
			return;
		}
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, true);
		// Location location =
		// locationManager.getLastKnownLocation(bestProvider);
		locationManager.requestLocationUpdates(provider, 0, 0, this);
	}

	private void showLocationSettingsDialog() {
		final Context context = getApplicationContext();
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage(context.getResources().getString(
				R.string.gps_network_not_enabled));
		dialog.setPositiveButton(R.string.open_location_settings,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						openSecuritySettings();
					}
				});
		dialog.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						// nothing done
					}
				});
		dialog.show();
	}

	private void openSecuritySettings() {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplicationContext().startActivity(intent);
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		if (locationManager == null || provider == null) {
			init();// try to init.
		}
		// everything is good now
		if (locationManager != null && provider != null) {
			locationManager.requestLocationUpdates(provider, 0, 0, this);
		}
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		if (locationManager != null) {
			locationManager.removeUpdates(this);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		this.provider = provider; // update provider?
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public LocationManager getLocationManager() {
		return locationManager;
	}

	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}
