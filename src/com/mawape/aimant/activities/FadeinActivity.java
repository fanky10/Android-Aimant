package com.mawape.aimant.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mawape.aimant.R;

public class FadeinActivity extends Activity implements AnimationListener {
	private enum Movement {
		UP, DOWN
	};

	private static final int ANIMATION_DURATION = 1000;

	private boolean isMenuOpen = false;
	private View topMenuLayout;

	private Display display;

	// Values for after the animation
	private int oldTop;
	private int newTop = -1;
	private int animToPostion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fadein);
		init();
	}

	private void init() {
		display = getWindowManager().getDefaultDisplay();
		topMenuLayout = findViewById(R.id.fadeinLayoutContainer);
		Button btnAction = (Button) findViewById(R.id.fadeinButton);

		animToPostion = topMenuLayout.getHeight();
		// RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
		// RelativeLayout.LayoutParams.MATCH_PARENT, animToPostion);
		// topMenuLayout.setLayoutParams(params);
		Log.d("fanky-animation", "aim to position: " + animToPostion);

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
		topMenuLayout.setVisibility(View.VISIBLE);
		animate(Movement.DOWN);

	}

	private void animSlideUp() {
		animate(Movement.UP);
	}

	private void animate(Movement movement) {
	    DisplayMetrics dm = new DisplayMetrics();
	    this.getWindowManager().getDefaultDisplay().getMetrics( dm );
	    int statusBarOffset = dm.heightPixels - topMenuLayout.getMeasuredHeight();
	    
	    TranslateAnimation animation;
		if (movement == Movement.DOWN) {
			animation = new TranslateAnimation(0, 0, - topMenuLayout.getMeasuredHeight(),
					statusBarOffset);
		} else if (movement == Movement.UP) {
			animation = new TranslateAnimation(0, 0, newTop, 0);
		}else{
			throw new IllegalArgumentException("unkown movement: "+movement);
		}
		animation.setDuration(ANIMATION_DURATION);
		animation.setFillEnabled(true);
		animation.setAnimationListener(this);
		animation.setInterpolator(new LinearInterpolator());
		topMenuLayout.startAnimation(animation);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// check for fade in animation
		if (isMenuOpen) {
			Toast.makeText(getApplicationContext(), "Menu is closed now",
					Toast.LENGTH_SHORT).show();
			isMenuOpen = false;
			topMenuLayout.setVisibility(View.GONE);
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
