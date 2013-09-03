package com.mawape.aimant.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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
