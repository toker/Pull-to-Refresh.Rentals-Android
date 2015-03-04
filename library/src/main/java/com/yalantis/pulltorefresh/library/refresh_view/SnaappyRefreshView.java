package com.yalantis.pulltorefresh.library.refresh_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.animation.Animation;

import com.yalantis.pulltorefresh.library.PullToRefreshView;
import com.yalantis.pulltorefresh.library.R;
import com.yalantis.pulltorefresh.library.util.Utils;

/**
 * Created by toker-rg on 02.03.15.
 */

public class SnaappyRefreshView extends BaseRefreshView {

    private static final String LOG_TAG = "SnaappyRefreshView";

	private static final float SCALE_START_PERCENT = 0.5f;

	private final static float SKY_RATIO = 0.65f;
	private static final float SKY_INITIAL_SCALE = 1.05f;

	private PullToRefreshView mParent;
	private Matrix mMatrix;
	private Animation mAnimation;

	private int mTop;
	private int mScreenWidth;

	private int mSkyHeight;
	private float mSkyTopOffset;
	private float mSkyMoveOffset;

	private float mPercent = 0.0f;

	private Bitmap mSky;


	public SnaappyRefreshView(Context context, PullToRefreshView parent) {
		super(context, parent);
		mParent = parent;
		mMatrix = new Matrix();

		initiateDimens();
		createBitmaps();
		//setupAnimations();
	}

	private void initiateDimens() {
		mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        Log.i(LOG_TAG, String.format("Init ScreenWidth: %d", mScreenWidth));

		mSkyHeight = (int) (SKY_RATIO * mScreenWidth);
		mSkyTopOffset = (mSkyHeight * 0.37f);
		mSkyMoveOffset = Utils.convertDpToPixel(getContext(), 20);

		mTop = -mParent.getTotalDragDistance();
        Log.i(LOG_TAG, String.format("Init mTop: %d", mScreenWidth));
	}

	private void createBitmaps() {
		mSky = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.kawa);
		mSky = Bitmap.createScaledBitmap(mSky, mScreenWidth, mSkyHeight, true);
	}

	@Override
	public void offsetTopAndBottom(int offset) {
		mTop += offset;
		invalidateSelf();
	}

	@Override
	public void setPercent(float percent, boolean invalidate) {
		setPercent(percent);
	}

	public void setPercent(float percent) {
		mPercent = percent;
	}

	@Override
	public void draw(Canvas canvas) {
		final int saveCount = canvas.save();
		//canvas.translate(0, mTop);

		drawSky(canvas);
		/*drawSun(canvas);
		drawTown(canvas);*/

		canvas.restoreToCount(saveCount);
	}

	private void drawSky(Canvas canvas) {
		Matrix matrix = mMatrix;
		matrix.reset();

		float dragPercent = Math.min(1f, Math.abs(mPercent));
        Log.i(LOG_TAG, String.format("dragPercent: %f", dragPercent));

		float skyScale;
		//float scalePercentDelta = dragPercent - SCALE_START_PERCENT;
		// ------------------------------------------------------------
		//NOTE  This part is responsible for scaling relatively to dragging
		/*if (scalePercentDelta > 0) {
			float scalePercent = scalePercentDelta / (1.0f - SCALE_START_PERCENT);
			skyScale = SKY_INITIAL_SCALE - (SKY_INITIAL_SCALE - 1.0f) * scalePercent;
		} else {
			skyScale = SKY_INITIAL_SCALE;
		}*/
        // ------------------------------------------------------------
        //skyScale = SKY_INITIAL_SCALE;

        skyScale = dragPercent;

		float offsetX = (mScreenWidth - mScreenWidth * skyScale) / 2.0f;
		/*float offsetY = (1.0f - dragPercent) * mParent.getTotalDragDistance()
				- mSkyTopOffset // Offset canvas moving
				- mSkyHeight * (skyScale - 1.0f) / 2 // Offset sky scaling
				+ mSkyMoveOffset * dragPercent; // Give it a little move top -> bottom*/
        float offsetY = 0;

        /*(0.25, 4)
        * (0.49, 2)*/


        matrix.postScale(skyScale, skyScale);
        matrix.postTranslate(offsetX, offsetY);
		canvas.drawBitmap(mSky, matrix, null);
	}

	@Override
	public void start() {
		/*mAnimation.reset();
		isRefreshing = true;
		mParent.startAnimation(mAnimation);*/
	}

	@Override
	public void stop() {
		/*mParent.clearAnimation();
		isRefreshing = false;
		resetOriginals();*/
	}

	@Override
	public boolean isRunning() {
		return false;
	}
}