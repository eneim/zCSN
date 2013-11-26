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
	public String mItemLink;
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
		mItemLink = mIntent.getStringExtra("link");		

		Log.d("itemLink", mItemLink + "");	
		mServiceIntent = new Intent(MusicPlayerActivity.this, MusicPlayerService.class);		
		mServiceIntent.putExtra("itemLink", mItemLink);
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