package com.mawape.aimant.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mawape.aimant.R;

public class NegocioMapActivity extends Activity implements LocationListener {
	private GoogleMap googleMap;
	private LocationManager locationManager;
	private String provider;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		init();
	}

	private void init() {
		// googleMap initialization
		googleMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();
		if (googleMap == null) {
			throw new IllegalArgumentException("googleMap not found!");
		}
		googleMap.setMyLocationEnabled(true);

		// location initialization
		if (isLocationManagerConfigured()) {
			findLocation();
			centerLocation();
		}

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
		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, true);
		location = locationManager
				.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		locationManager.requestLocationUpdates(provider, 0, 0, this);
		return true;
	}

	private void findLocation() {
		try {
			Geocoder geocoder = new Geocoder(getApplicationContext());
			List<Address> addresses = new ArrayList<Address>();
			addresses = geocoder.getFromLocationName(
					"Angelome 2232 Funes Santa Fe Argentina", 1);
			if (addresses.size() > 0) {
				double latitude = addresses.get(0).getLatitude();
				double longitude = addresses.get(0).getLongitude();
				LatLng latLong = new LatLng(0, 0);
				location.setLatitude(latitude);
				location.setLongitude(longitude);
			}
		} catch (IOException ex) {
			Log.d(getClass().getName(),
					"ioexception geocoder:" + ex.getMessage());
		}
	}

	private void centerLocation() {
		LatLng data = new LatLng(location.getLatitude(),
				location.getLongitude());
		CameraUpdate center = CameraUpdateFactory.newLatLng(data);
		CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
		googleMap.addMarker(new MarkerOptions()
        .position(data)
        .title("Un nombre"));
		googleMap.moveCamera(center);
		googleMap.animateCamera(zoom);
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

	public GoogleMap getGoogleMap() {
		return googleMap;
	}

	public void setGoogleMap(GoogleMap googleMap) {
		this.googleMap = googleMap;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
