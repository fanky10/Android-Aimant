package com.mawape.aimant;

import android.app.Application;

import com.mawape.aimant.constants.AppConstants;
import com.testflightapp.lib.TestFlight;

public class AimantApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// Initialize TestFlight with your app token.
		TestFlight.takeOff(this, AppConstants.TEST_FLIGHT_TOKEN);

	}
}
