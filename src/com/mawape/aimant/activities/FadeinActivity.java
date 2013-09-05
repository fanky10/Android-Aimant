package com.mawape.aimant.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

import com.mawape.aimant.R;

public class FadeinActivity extends Activity implements AnimationListener {
	private Animation animFedIn;
	private boolean isMenuOpen = false;
	private View topMenu;
	
	private Display display;
	// Values for after the animation
	private int oldLeft;
	private int oldTop;
	private int newleft;
	private int newTop;
	private int screenWidth;
	private int animToPostion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fadein);
		init();
	}

	@SuppressWarnings("deprecation")
	private void init() {
		display = getWindowManager().getDefaultDisplay();
		topMenu = findViewById(R.id.fadeinLayoutContainer);
		Button btnAction = (Button) findViewById(R.id.fadeinButton);
		
		screenWidth = display.getWidth();
				
//		int calcAnimationPosition = (screenWidth / 2);
		// Value where the onTop Layer has to animate
		// also the max width of the layout underneath
		// Set Layout params for subLayout according to calculation
		animToPostion = topMenu.getHeight();
		Log.d("fanky-animation","aim to position: "+animToPostion);
		
		btnAction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!isMenuOpen) {
					// slide down
					animSlideDown();
				} else {
					animSlideUp();
				}

			}
		});
	}

	private void animSlideDown() {
		topMenu.setVisibility(View.VISIBLE);
		newTop = topMenu.getTop() + animToPostion;

		TranslateAnimation slideDown = new TranslateAnimation(0, 0, 0, newTop);
		slideDown.setDuration(500);
		slideDown.setFillEnabled(true);
		slideDown.setAnimationListener(this);
		topMenu.startAnimation(slideDown);
	}

	private void animSlideUp() {
		topMenu.setVisibility(View.GONE);
		oldTop = topMenu.getTop() - animToPostion;

		TranslateAnimation slideUp = new TranslateAnimation(0, 0, newTop, oldTop);
		slideUp.setDuration(500);
		slideUp.setFillEnabled(true);
		slideUp.setAnimationListener(this);
		topMenu.startAnimation(slideUp);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// check for fade in animation
		if (isMenuOpen) {
			Toast.makeText(getApplicationContext(), "Menu is closed now",
					Toast.LENGTH_SHORT).show();
			isMenuOpen = false;
		} else {
			Toast.makeText(getApplicationContext(), "Menu is open",
					Toast.LENGTH_SHORT).show();
			isMenuOpen = true;
		}

	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub

	}

}
