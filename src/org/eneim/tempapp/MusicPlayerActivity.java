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
		Log.d("player view", "player view is set");

		mServiceIntent = new Intent(MusicPlayerActivity.this, MusicPlayerService.class);		
		mServiceIntent.putExtra("itemLink", mItemLink);
		startService(mServiceIntent);
		
		//new CSNPlayerViewLoader().execute(mItemLink);
	}

	public class CSNPlayerViewLoader extends AsyncTask<String, Void, CSNMusicItem> {
		ProgressDialog prog;
		Handler innerHandler;

		@Override
		protected void onPreExecute() { 

			prog = new ProgressDialog(MusicPlayerActivity.this); 
			prog.setMessage("Loading ..."); 
			prog.show();
		}

		@Override
		protected CSNMusicItem doInBackground(String... params) {
			for (String urlVal : params) { 
				Log.d("NAM", urlVal);	
				CSNMusicItemParser mMusicItemParser = new CSNMusicItemParser();
				mItem = mMusicItemParser.parse(urlVal);
			}

			return mItem;
		}

		@Override
		protected void onPostExecute(CSNMusicItem item) {
			prog.dismiss();	

			Bundle itemBundle = new Bundle();
			itemBundle.putString("songTitle", mItem.getTitle());
			itemBundle.putString("songLinktoPlay", mItem.getLinkToPlay());
			itemBundle.putString("songLength", mItem.getLength());
			itemBundle.putString("songCover", mItem.getCoverURL());
			itemBundle.putString("songID", mItem.getID());
			
			itemBundle.putStringArray("songPerformers", 
								mItem.getPerformer().toArray(new String[mItem.getPerformer().size()]));

			playerService = new Intent(MusicPlayerActivity.this, MusicPlayerService.class);
			playerService.putExtra("musicItem", itemBundle);
			startService(playerService);
		}

		@Override
		protected void onProgressUpdate(Void... values) {						

		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (!MusicPlayerService.mp.isPlaying()) {
			stopService(playerService);
		}
	}

}