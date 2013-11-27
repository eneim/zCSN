package org.eneim.tempapp;

import org.eneim.tempapp.items.CSNMusicItem;
import org.eneim.tempapp.parser.CSNMusicItemParser;
import org.eneim.tempapp.services.MusicPlayerService;
import org.eneim.tempapp.view.MusicPlayerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MusicPlayerActivity extends Activity {

	public MusicPlayerView playerView;

	public Intent playerService;
	public Intent mIntent, mServiceIntent;
	public int mIndex;
	public String[] mItemLinkList;
	public CSNMusicItem mItem;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		//setContentView(R.layout.player);
		playerView = new MusicPlayerView(this);
		setContentView(playerView);

		initPlayer();
	}

	public void initPlayer() {
		mIntent = getIntent();
		mIndex = mIntent.getIntExtra("mIndex", -1);		
		mItemLinkList = mIntent.getStringArrayExtra("itemLinkList");
		
		mServiceIntent = new Intent(MusicPlayerActivity.this, MusicPlayerService.class);		
		mServiceIntent.putExtra("mIndex", mIndex);
		mServiceIntent.putExtra("itemLinkList", mItemLinkList);
		startService(mServiceIntent);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (!MusicPlayerService.mp.isPlaying()) {
			stopService(mServiceIntent);
		}
	}

}