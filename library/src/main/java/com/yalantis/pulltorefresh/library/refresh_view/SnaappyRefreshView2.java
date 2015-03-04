package com.yalantis.pulltorefresh.library.refresh_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.yalantis.pulltorefresh.library.PullToRefreshView;
import com.yalantis.pulltorefresh.library.R;

/**
 * Created by toker-rg on 04.03.15.
 */
public class SnaappyRefreshView2 extends View implements Animatable {

	private static final String LOG_TAG = "SnaappyRefreshView2";

	private final float RATIO = 0.55f;

	private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
	private static final int ANIMATION_DURATION = 1000;

	private PullToRefreshView mParent;
	private Matrix mMatrix;
	private Animation mAnimation;

	private Bitmap mBitmap;
	private int mBitmapHeight;

	private int mTop;
	private int mScreenWidth;

	private float mPercent = 0.0f;

	private boolean isRefreshing = false;


	public SnaappyRefreshView2(Context context, PullToRefreshView layout) {
		super(context);
		mParent = layout;
		mMatrix = new Matrix();

		initiateDimens();
		createBitmaps();
		setupAnimations();
	}

	private void initiateDimens() {
		mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;

		mBitmapHeight = (int) (RATIO * mScreenWidth);
	}

	private void createBitmaps() {
		mScreenWidth = mScreenWidth*4/5;
		mBitmapHeight = mBitmapHeight*4/5;

		mBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.kawa);
		mBitmap = Bitmap.createScaledBitmap(mBitmap, mScreenWidth, mBitmapHeight, true);
	}

	/**
	 * Set current drag percent
	 * @param percent
	 * @param invalidate
	 */
	public void setPercent(float percent, boolean invalidate) {
		mPercent = percent;

		if (invalidate)
			invalidate();
	}

	public void applyOffsetTop(int offset) {
		mTop += offset;
		invalidate();
	}

	public void resetOriginals() {
		mPercent = 0;
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		drawRefreshView(canvas);
	}

	private void drawRefreshView(Canvas canvas) {
		Matrix matrix = mMatrix;
		matrix.reset();

		float dragPercent = Math.min(1f, Math.abs(mPercent));
		Log.i(LOG_TAG, String.format("dragPercent: %f", dragPercent));

		float bitmapScale;
		bitmapScale = dragPercent;

		float offsetX = (mScreenWidth - mScreenWidth * bitmapScale) / 2.0f;
		float offsetY = 0;

		matrix.postScale(bitmapScale, bitmapScale);
		matrix.postTranslate(offsetX, offsetY);
		canvas.drawBitmap(mBitmap, matrix, null);
	}

	@Override
	public boolean isRunning() {
		return false;
	}

	@Override
	public void start() {
		mAnimation.reset();
		isRefreshing = true;
		mParent.startAnimation(mAnimation);
	}

	@Override
	public void stop() {
		mParent.clearAnimation();
		isRefreshing = false;
		resetOriginals();
	}

	private void setupAnimations() {
		mAnimation = new Animation() {
			@Override
			public void applyTransformation(float interpolatedTime, Transformation t) {
				//setRotate(interpolatedTime);
			}
		};
		mAnimation.setRepeatCount(Animation.INFINITE);
		mAnimation.setRepeatMode(Animation.RESTART);
		mAnimation.setInterpolator(LINEAR_INTERPOLATOR);
		mAnimation.setDuration(ANIMATION_DURATION);
	}
}
