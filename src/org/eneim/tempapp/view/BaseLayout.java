package org.eneim.tempapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class BaseLayout extends RelativeLayout {
	protected Context mContext;
	
	public BaseLayout(Context context, AttributeSet attr) {
		super(context, attr);
		mContext = context;
		
		/*
		 * when extending this class as new BaseLayout(thatContext, attr), 
		 * extended class will use thatContext as its context;
		 */
	}
	
	public BaseLayout(Context context, int resID) {
		super(context);
		mContext = context;
		inflate(context, resID, this);
		
		/*
		 * call this contract to inflate the resource (defined by resID) to
		 * the recent resource (view, layout etc.) (defined by context)
		 */
	}

}
