package org.eneim.tempapp.fragments;

import org.eneim.tempapp.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainFragmentActivity extends BaseActivity {

	private ViewPager mViewPager;
	private Context mContext;
	private SlidingMenu mSlidingMenu;
	
	public MainFragmentActivity() {
		super(R.string.app_name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		
		mViewPager = new ViewPager(this);
		mViewPager.setId("mainUI".hashCode());
		mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
		setContentView(mViewPager);	
		
		mSlidingMenu = getSlidingMenu();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu mMenu) {
		MenuInflater mMenuInflater = getSupportMenuInflater();
		mMenuInflater.inflate(R.menu.main, mMenu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(mContext, "refresh press", Toast.LENGTH_SHORT).show();
			Log.d("Action Menu test", item.getItemId() + "");
			break;
		case android.R.id.home:
			toggle();		
		default:
			break;
		}	
		
		return true;
	}	

	public class MainPagerAdapter extends FragmentPagerAdapter {

		public MainPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
}
