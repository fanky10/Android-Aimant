package com.mawape.aimant.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mawape.aimant.R;

public class SlidePanel extends LinearLayout {

	private static final String TAG = "Panel";

	/**
	 * Callback invoked when the panel is opened/closed.
	 */
	public static interface OnPanelListener {
		/**
		 * Invoked when the panel becomes fully closed.
		 */
		public void onPanelClosed(SlidePanel panel);

		/**
		 * Invoked when the panel becomes fully opened.
		 */
		public void onPanelOpened(SlidePanel panel);
	}

	private boolean mIsShrinking;
	private int mPosition;
	private int mDuration;
	private boolean mLinearFlying;
	private int mHandleId;
	private int mContentId;
	private int mIconHandlerId;
	private View mHandle;
	private ImageView mIconHandler;
	private View mContent;
	private Drawable mOpenedHandle;
	private Drawable mClosedHandle;
	private float mTrackX;
	private float mTrackY;
	private float mVelocity;

	private OnPanelListener panelListener;

	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	private enum State {
		ABOUT_TO_ANIMATE, ANIMATING, READY, TRACKING, FLYING,
	};

	private State mState;
	private Interpolator mInterpolator;
	private GestureDetector mGestureDetector;
	private int mContentHeight;
	private int mContentWidth;
	private PanelOnGestureListener mGestureListener;
	private boolean mBringToFront;

	public SlidePanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SlidePanel);
		// duration default: 750ms
		mDuration = a.getInteger(R.styleable.SlidePanel_animationDuration, 750);
		// linearFlying: to false
		mLinearFlying = a
				.getBoolean(R.styleable.SlidePanel_linearFlying, false);

		mOpenedHandle = a.getDrawable(R.styleable.SlidePanel_openedHandle);
		mClosedHandle = a.getDrawable(R.styleable.SlidePanel_closedHandle);

		RuntimeException e = null;
		mHandleId = a.getResourceId(R.styleable.SlidePanel_handle, 0);
		if (mHandleId == 0) {
			e = new IllegalArgumentException(
					a.getPositionDescription()
							+ ": The handle attribute is required and must refer to a valid child.");
		}
		mContentId = a.getResourceId(R.styleable.SlidePanel_content, 0);
		if (mContentId == 0) {
			e = new IllegalArgumentException(
					a.getPositionDescription()
							+ ": The content attribute is required and must refer to a valid child.");
		}

		mIconHandlerId = a.getResourceId(R.styleable.SlidePanel_iconHandler, 0);
		if (mIconHandlerId == 0) {
			e = new IllegalArgumentException(
					a.getPositionDescription()
							+ ": The icon handlerId is required and must refer to a valid child.");
		}

		a.recycle();

		if (e != null) {
			throw e;
		}
		setOrientation(VERTICAL);
		mState = State.READY;
		if (!this.isInEditMode()) {
			mGestureListener = new PanelOnGestureListener();
			mGestureDetector = new GestureDetector(getContext(),
					mGestureListener);
			mGestureDetector.setIsLongpressEnabled(false);
		}
		// i DON'T really know why i need this...
		setBaselineAligned(false);
	}

	/**
	 * Sets the listener that receives a notification when the panel becomes
	 * open/close.
	 * 
	 * @param onPanelListener
	 *            The listener to be notified when the panel is opened/closed.
	 */
	public void setOnPanelListener(OnPanelListener onPanelListener) {
		panelListener = onPanelListener;
	}

	/**
	 * Gets Panel's mHandle
	 * 
	 * @return Panel's mHandle
	 */
	public View getHandle() {
		return mHandle;
	}

	/**
	 * Gets Panel's mContent
	 * 
	 * @return Panel's mContent
	 */
	public View getContent() {
		return mContent;
	}

	/**
	 * Sets the acceleration curve for panel's animation.
	 * 
	 * @param i
	 *            The interpolator which defines the acceleration curve
	 */
	public void setInterpolator(Interpolator i) {
		mInterpolator = i;
	}

	/**
	 * Set the opened state of Panel.
	 * 
	 * @param open
	 *            True if Panel is to be opened, false if Panel is to be closed.
	 * @param animate
	 *            True if use animation, false otherwise.
	 * 
	 * @return True if operation was performed, false otherwise.
	 * 
	 */
	public boolean setOpen(boolean open, boolean animate) {
		if (mState == State.READY && isOpen() ^ open) {
			mIsShrinking = !open;
			if (animate) {
				mState = State.ABOUT_TO_ANIMATE;
				if (!mIsShrinking) {
					// this could make flicker so we test mState in
					// dispatchDraw()
					// to see if is equal to ABOUT_TO_ANIMATE
					mContent.setVisibility(VISIBLE);
				}
				post(startAnimation);
			} else {
				mContent.setVisibility(open ? VISIBLE : GONE);
				postProcess();
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns the opened status for Panel.
	 * 
	 * @return True if Panel is opened, false otherwise.
	 */
	public boolean isOpen() {
		return mContent.getVisibility() == VISIBLE;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mHandle = findViewById(mHandleId);
		if (mHandle == null) {
			String name = getResources().getResourceEntryName(mHandleId);
			throw new RuntimeException(
					"Your Panel must have a child View whose id attribute is 'R.id."
							+ name + "'");
		}
		mHandle.setOnTouchListener(touchListener);
		mHandle.setOnClickListener(clickListener);

		mContent = findViewById(mContentId);
		if (mContent == null) {
			String name = getResources().getResourceEntryName(mHandleId);
			throw new RuntimeException(
					"Your Panel must have a child View whose id attribute is 'R.id."
							+ name + "'");
		}

		mIconHandler = (ImageView) findViewById(mIconHandlerId);
		if (mIconHandler == null) {
			String name = getResources().getResourceEntryName(mIconHandlerId);
			throw new RuntimeException(
					"Your Panel must have a child View whose id attribute is 'R.id."
							+ name + "'");
		}

		// reposition children
		removeView(mHandle);
		removeView(mContent);
		if (mPosition == TOP || mPosition == LEFT) {
			addView(mContent);
			addView(mHandle);
		} else {
			addView(mHandle);
			addView(mContent);
		}

		if (mOpenedHandle != null) {
			mIconHandler.setImageDrawable(mOpenedHandle);
		}
		mContent.setClickable(true);
		mContent.setVisibility(GONE);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		ViewParent parent = getParent();
		if (parent != null && parent instanceof FrameLayout) {
			mBringToFront = true;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		mContentWidth = mContent.getWidth();
		mContentHeight = mContent.getHeight();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		// this is why 'mState' was added:
		// avoid flicker before animation start

		// que onda con este drawer?? :P
		if (mState == State.ABOUT_TO_ANIMATE && !mIsShrinking) {
			int delta = mContentHeight;
			if (mPosition == LEFT || mPosition == TOP) {
				delta = -delta;
			}
			canvas.translate(0, delta);
		}
		if (mState == State.TRACKING || mState == State.FLYING) {
			canvas.translate(mTrackX, mTrackY);
		}
		super.dispatchDraw(canvas);
	}

	private float ensureRange(float v, int min, int max) {
		v = Math.max(v, min);
		v = Math.min(v, max);
		return v;
	}

	OnTouchListener touchListener = new OnTouchListener() {
		int initX;
		int initY;
		boolean setInitialPosition;

		public boolean onTouch(View v, MotionEvent event) {
			// Log.d(TAG, "state: " + mState + " x: " + event.getX() + " y: " +
			// event.getY());
			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				if (mBringToFront) {
					bringToFront();
				}
				initX = 0;
				initY = 0;
				if (mContent.getVisibility() == GONE) {
					// since we may not know content dimensions we use factors
					// here
					initY = mPosition == TOP ? -1 : 1;
				}
				setInitialPosition = true;
			} else {
				if (setInitialPosition) {
					// now we know content dimensions, so we multiply factors...
					initX *= mContentWidth;
					initY *= mContentHeight;
					// ... and set initial panel's position
					mGestureListener.setScroll(initX, initY);
					setInitialPosition = false;
					// for offsetLocation we have to invert values
					initX = -initX;
					initY = -initY;
				}
				// offset every ACTION_MOVE & ACTION_UP event
				event.offsetLocation(initX, initY);
			}
			if (!mGestureDetector.onTouchEvent(event)) {
				if (action == MotionEvent.ACTION_UP) {
					// tup up after scrolling
					post(startAnimation);
				}
			}
			return false;
		}
	};

	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			if (mBringToFront) {
				bringToFront();
			}
			initChange();
			post(startAnimation);
		}
	};

	public void initChange() {
		if (mState != State.READY) {
			// we are animating or just about to animate
			return;
		}
		mState = State.ABOUT_TO_ANIMATE;
		mIsShrinking = mContent.getVisibility() == VISIBLE;
		if (!mIsShrinking) {
			// this could make flicker so we test mState in dispatchDraw()
			// to see if is equal to ABOUT_TO_ANIMATE
			mContent.setVisibility(VISIBLE);
		}
	}

	Runnable startAnimation = new Runnable() {
		public void run() {
			// this is why we post this Runnable couple of lines above:
			// now its save to use mContent.getHeight() && mContent.getWidth()
			TranslateAnimation animation;
			int fromXDelta = 0, toXDelta = 0, fromYDelta = 0, toYDelta = 0;
			if (mState == State.FLYING) {
				mIsShrinking = (mPosition == TOP || mPosition == LEFT)
						^ (mVelocity > 0);
			}
			int calculatedDuration;
			int height = mContentHeight;
			if (!mIsShrinking) {
				fromYDelta = mPosition == TOP ? -height : height;
			} else {
				toYDelta = mPosition == TOP ? -height : height;
			}
			if (mState == State.TRACKING) {
				if (Math.abs(mTrackY - fromYDelta) < Math.abs(mTrackY
						- toYDelta)) {
					mIsShrinking = !mIsShrinking;
					toYDelta = fromYDelta;
				}
				fromYDelta = (int) mTrackY;
			} else if (mState == State.FLYING) {
				fromYDelta = (int) mTrackY;
			}
			// for FLYING events we calculate animation duration based on
			// flying velocity
			// also for very high velocity make sure duration >= 20 ms
			if (mState == State.FLYING && mLinearFlying) {
				calculatedDuration = (int) (1000 * Math
						.abs((toYDelta - fromYDelta) / mVelocity));
				calculatedDuration = Math.max(calculatedDuration, 20);
			} else {
				calculatedDuration = mDuration
						* Math.abs(toYDelta - fromYDelta) / mContentHeight;
			}

			mTrackX = mTrackY = 0;
			if (calculatedDuration == 0) {
				mState = State.READY;
				if (mIsShrinking) {
					mContent.setVisibility(GONE);
				}
				postProcess();
				return;
			}

			animation = new TranslateAnimation(fromXDelta, toXDelta,
					fromYDelta, toYDelta);
			animation.setDuration(calculatedDuration);
			animation.setAnimationListener(animationListener);
			if (mState == State.FLYING && mLinearFlying) {
				animation.setInterpolator(new LinearInterpolator());
			} else if (mInterpolator != null) {
				animation.setInterpolator(mInterpolator);
			}
			startAnimation(animation);
		}
	};

	private AnimationListener animationListener = new AnimationListener() {
		public void onAnimationEnd(Animation animation) {
			mState = State.READY;
			if (mIsShrinking) {
				mContent.setVisibility(GONE);
			}
			postProcess();
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
			mState = State.ANIMATING;
		}
	};

	private void postProcess() {

		if (mIsShrinking && mOpenedHandle != null) {
			mIconHandler.setImageDrawable(mOpenedHandle);
		} else if (!mIsShrinking && mClosedHandle != null) {
			mIconHandler.setImageDrawable(mClosedHandle);
		}
		// invoke listener if any
		if (panelListener != null) {
			if (mIsShrinking) {
				panelListener.onPanelClosed(SlidePanel.this);
			} else {
				panelListener.onPanelOpened(SlidePanel.this);
			}
		}
	}

	class PanelOnGestureListener implements OnGestureListener {
		float scrollY;
		float scrollX;

		public void setScroll(int initScrollX, int initScrollY) {
			scrollX = initScrollX;
			scrollY = initScrollY;
		}

		public boolean onDown(MotionEvent e) {
			scrollX = scrollY = 0;
			initChange();
			return true;
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			mState = State.FLYING;
			mVelocity = velocityY;
			post(startAnimation);
			return true;
		}

		public void onLongPress(MotionEvent e) {
			// not used
		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			mState = State.TRACKING;
			float tmpY = 0, tmpX = 0;
			scrollY -= distanceY;
			if (mPosition == TOP) {
				tmpY = ensureRange(scrollY, -mContentHeight, 0);
			} else {
				tmpY = ensureRange(scrollY, 0, mContentHeight);
			}
			if (tmpX != mTrackX || tmpY != mTrackY) {
				mTrackX = tmpX;
				mTrackY = tmpY;
				invalidate();
			}
			return true;
		}

		public void onShowPress(MotionEvent e) {
			// not used
		}

		public boolean onSingleTapUp(MotionEvent e) {
			// not used
			return false;
		}
	}
}