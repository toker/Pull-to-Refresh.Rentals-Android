package com.yalantis.pulltorefresh.library.refresh_view;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;

import com.yalantis.pulltorefresh.library.PullToRefreshView;

public abstract class BaseRefreshView extends Drawable
										implements Drawable.Callback, Animatable {

	//NOTE figure out what for it uses here
    private PullToRefreshView mParentLayout;

    public BaseRefreshView(Context context, PullToRefreshView layout) {
        mParentLayout = layout;
    }

    public Context getContext() {
        return mParentLayout != null ? mParentLayout.getContext() : null;
    }

    public PullToRefreshView getParentLayout(){
        return mParentLayout;
    }

    public abstract void setPercent(float percent, boolean invalidate);

    public abstract void offsetTopAndBottom(int offset);

    @Override
    public void invalidateDrawable(Drawable who) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }
}
