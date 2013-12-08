package org.eneim.tempapp.fragments;

import java.util.ArrayList;

import org.eneim.tempapp.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainFragmentActivity extends SlidingFragmentActivity {

	private Context mContext;
	private ViewPager mViewPager;
	private MainListFragmentAdapter mListAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pager);
		setBehindContentView(R.layout.menu_frame);

		mContext = this;
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mListAdapter = new MainListFragmentAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mListAdapter);
		//mViewPager.setCurrentItem(0);
		
		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) { }

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) { }

			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					//getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					
					break;
				default:
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
					break;
				}
			}

		});
		
		mViewPager.setCurrentItem(0);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public class MainListFragmentAdapter extends FragmentPagerAdapter {

		private ArrayList<Fragment> mFragments;
		
		private FragmentManager mFragmentManager;
		
		public MainListFragmentAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
			mFragments = new ArrayList<Fragment>();
			mFragments.add(new DataListFragment());
			mFragments.add(new DataListFragment());
			mFragments.add(new DataListFragment());
			mFragments.add(new DataListFragment());
			
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub			
			return mFragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mFragments.size();
		}
		
	}
}
