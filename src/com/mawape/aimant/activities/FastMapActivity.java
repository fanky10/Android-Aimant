package com.mawape.aimant.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mawape.aimant.R;
import com.mawape.aimant.entities.Negocio;

public class FastMapActivity extends BaseActivity {
	private long activityInit = System.currentTimeMillis();
	private GoogleMap googleMap;
	private LocationManager locationManager;
	private Negocio negocioSeleccionado;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);

		negocioSeleccionado = new Negocio("Test", "Santa Fe 1300");
		if (isMapInitialized()) {
			postMapConfig();
		}
		Log.d("fast-map", "it took"
				+ (System.currentTimeMillis() - activityInit) + "ms");

	}

	protected boolean isMapInitialized() {
		Boolean result = true;
		// googleMap initialization
		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// Google Play Services are not available
		if (status != ConnectionResult.SUCCESS) {
			showGMapNotFoundDialog(R.string.google_play_services_outdated);
			result = false;
		}

		googleMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		if (googleMap == null) {
			showGMapNotFoundDialog(R.string.google_map_not_found);
			result = false;
		}
		// no more location initialization
		if (!isLocationManagerConfigured()) {
			result = false;
		}
		return result;

	}

	private void showGMapNotFoundDialog(int messageId) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage(messageId);
		dialog.setPositiveButton(R.string.open_location_settings,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface paramDialogInterface,
							int paramInt) {
						openSecuritySettings();
					}
				});
		dialog.show();
	}

	private boolean isLocationManagerConfigured() {
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
			return false;
		}
		return true;
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

	private void postMapConfig() {
		mapSettings();
		StringBuilder direccion = new StringBuilder();
		direccion.append(negocioSeleccionado.getDireccion());
		direccion.append(getString(R.string.default_localidad));
		markLocation(negocioSeleccionado.getNombre(), direccion.toString());
	}

	private void mapSettings() {
		UiSettings gmapSettings = googleMap.getUiSettings();
		gmapSettings.setMyLocationButtonEnabled(false);
		gmapSettings.setZoomControlsEnabled(false);
		gmapSettings.setCompassEnabled(false);
	}

	private void markLocation(String title, String direccion) {
		try {
			Geocoder geocoder = new Geocoder(getApplicationContext());
			List<Address> addresses = new ArrayList<Address>();
			addresses = geocoder.getFromLocationName(direccion, 1);
			if (addresses.size() > 0) {
				double latitude = addresses.get(0).getLatitude();
				double longitude = addresses.get(0).getLongitude();
				LatLng data = new LatLng(latitude, longitude);
				CameraUpdate center = CameraUpdateFactory.newLatLng(data);
				CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
				googleMap.addMarker(new MarkerOptions().position(data).title(
						title));
				googleMap.moveCamera(center);
				googleMap.animateCamera(zoom);
			}
		} catch (IOException ex) {
			Log.d(getClass().getName(),
					"ioexception geocoder:" + ex.getMessage());
		}

	}

}
