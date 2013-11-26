package org.eneim.tempapp.view;

import android.content.Context;
import android.util.AttributeSet;


public class SquareImageView extends LayoutSuppressingImageView {

    /**
     * @param context The {@link Context} to use
     * @param attrs The attributes of the XML tag that is inflating the view.
     */
    public SquareImageView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onMeasure(final int widthSpec, final int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        final int mSize = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(mSize, mSize);
    }

}
