package org.eneim.tempapp.fragments;

import java.util.ArrayList;

import org.eneim.tempapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainFragmentActivity extends SlidingFragmentActivity {

	//private Context mContext;
	private ViewPager mViewPager;
	private MainListFragmentAdapter mListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// layout out viewpager on the top
		setContentView(R.layout.activity_pager);
		
		// menu is set to behind
		setBehindContentView(R.layout.menu_frame);

		//mContext = this;
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mListAdapter = new MainListFragmentAdapter(getSupportFragmentManager());
		
		mViewPager.setAdapter(mListAdapter);
		
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

		// customize the SlidingMenu
		SlidingMenu mSlidingMenu = getSlidingMenu();
		mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
		mSlidingMenu.setShadowDrawable(R.drawable.shadow);
		mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSlidingMenu.setFadeDegree(0.25f);
		mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		//mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		
		ActionBar actionBar = getSupportActionBar();
		
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setNavigationMode(com.actionbarsherlock.app.ActionBar.NAVIGATION_MODE_TABS);
		
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

		//private FragmentManager mFragmentManager;
		private DataListFragment3 mListFragment;
		
		private ArrayList<Fragment> mFragments;
		
		public MainListFragmentAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<Fragment>();

			mFragments.add(new DataListFragment3(getApplicationContext(), "http://chiasenhac.com/mp3/vietnam/"));
			mFragments.add(new DataListFragment3(getApplicationContext(), "http://chiasenhac.com/mp3/us-uk/"));
			mFragments.add(new DataListFragment3(getApplicationContext(), "http://chiasenhac.com/mp3/chinese/"));
			mFragments.add(new DataListFragment3(getApplicationContext(), "http://chiasenhac.com/mp3/korea/"));
			mFragments.add(new DataListFragment3(getApplicationContext(), "http://chiasenhac.com/mp3/other/"));
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub	
			mListFragment = (DataListFragment3) mFragments.get(arg0);
			
			return mListFragment;
			//return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mFragments.size();
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			
			//return "OBJECT " + (position + 1);
			switch(position) {
				case 0:
					return "Việt Nam";
				case 1:
					return "Âu, Mỹ";
				case 2:
					return "Nhạc Hoa";
				case 3:
					return "Nhạc Hàn";
				case 4:
					return "Nước khác";
			}
				
			return super.getPageTitle(position);
		}

	}	
}
