/**
 * Copyright (C) 2015 - Mazarin Pvt Ltd
 * @author Tharaka Nirmana
 */
package com.mazarin_movieApp.util;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;


public class GravityCompoundDrawable extends Drawable {

    // inner Drawable
    private final Drawable mDrawable;

    public GravityCompoundDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    @Override
    public int getIntrinsicWidth() {
        return mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mDrawable.getIntrinsicHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        int halfCanvas= canvas.getHeight() / 2;
        int halfDrawable = mDrawable.getIntrinsicHeight() / 2;

        // align to top
        canvas.save();
        canvas.translate(0, -halfCanvas + halfDrawable);
        mDrawable.draw(canvas);
        canvas.restore();
    }

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#getOpacity()
	 */
	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#setAlpha(int)
	 */
	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see android.graphics.drawable.Drawable#setColorFilter(android.graphics.ColorFilter)
	 */
	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}
}