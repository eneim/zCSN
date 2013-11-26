package org.eneim.tempapp.services;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.eneim.tempapp.R;
import org.eneim.tempapp.utilities.Utilities;
import org.eneim.tempapp.view.MusicPlayerView;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class MusicPlayerService extends Service implements OnCompletionListener,
OnClickListener, OnSeekBarChangeListener {

	private WeakReference<ImageButton> btnRepeat, btnShuffle;
	private WeakReference<ImageView> btnPlay, btnForward, btnBackward, btnNext,
	btnPrevious;
	private WeakReference<SeekBar> songProgressBar;
	private WeakReference<TextView> songTitleLabel;
	private WeakReference<ImageView> songCover;
	private WeakReference<TextView> songCurrentDurationLabel;
	private WeakReference<TextView> songTotalDurationLabel;
	public static MediaPlayer mp;
	private Handler progressBarHandler = new Handler();;
	private Utilities utils;
	private int seekForwardTime = 5000; // 5000 milliseconds
	private int seekBackwardTime = 5000; // 5000 milliseconds
	private boolean isShuffle = false;
	private boolean isRepeat = false;
	private ArrayList<HashMap<String, String>> songsListingSD = new ArrayList<HashMap<String, String>>();
	public static String currentSong = null;

	public static String linktoplay;

	private Bundle mItemBundle;
	private int lastSondId;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		mp = new MediaPlayer();
		mp.setOnCompletionListener(this);
		mp.reset();
		mp.setAudioStreamType(AudioManager.STREAM_MUSIC);//
		utils = new Utilities();
		songsListingSD = MusicPlayerView.songsList;
		songCurrentDurationLabel = new WeakReference<TextView>(
				MusicPlayerView.songCurrentDurationLabel);
		super.onCreate();

	}

	/**
	 * Init UI
	 */
	private void initUI() {
		songTitleLabel = new WeakReference<TextView>(MusicPlayerView.songTitleLabel);
		songCover = new WeakReference<ImageView>(MusicPlayerView.songCover);
		songCurrentDurationLabel = new WeakReference<TextView>(
				MusicPlayerView.songCurrentDurationLabel);
		songTotalDurationLabel = new WeakReference<TextView>(
				MusicPlayerView.songTotalDurationLabel);

		btnPlay = new WeakReference<ImageView>(MusicPlayerView.btnPlay);
		btnForward = new WeakReference<ImageView>(MusicPlayerView.btnForward);
		btnBackward = new WeakReference<ImageView>(MusicPlayerView.btnBackward);
		btnNext = new WeakReference<ImageView>(MusicPlayerView.btnNext);
		btnPrevious = new WeakReference<ImageView>(MusicPlayerView.btnPrevious);
		btnRepeat = new WeakReference<ImageButton>(MusicPlayerView.btnRepeat);
		btnShuffle = new WeakReference<ImageButton>(MusicPlayerView.btnShuffle);

		btnPlay.get().setOnClickListener(this);
		btnForward.get().setOnClickListener(this);
		btnBackward.get().setOnClickListener(this);
		btnNext.get().setOnClickListener(this);
		btnPrevious.get().setOnClickListener(this);
		btnRepeat.get().setOnClickListener(this);
		btnShuffle.get().setOnClickListener(this);
		// TODO Auto-generated method stub

		songProgressBar = new WeakReference<SeekBar>(
				MusicPlayerView.songProgressBar);
		songProgressBar.get().setOnSeekBarChangeListener(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		initUI();
		mItemBundle = intent.getBundleExtra("musicItem");		
		String songLink = mItemBundle.getString("songLinktoPlay");
		String songId = mItemBundle.getString("songID");
		Log.d("songId | currentSong", songId + " | " + currentSong);
		
		if (songLink != null) {			
			playSong(songLink);
			currentSong = songId;
			//currentSongIndex = songIndex;
		} else {
			if (mp.isPlaying())
				btnPlay.get().setImageResource(R.drawable.btn_pause);
			else
				btnPlay.get().setImageResource(R.drawable.btn_play);
		}

		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	public synchronized void playSong(String link) {
		// Play song
		try {			
			mp.reset();
			mp.setDataSource(link);
			mp.prepare();
			mp.start();
			// Displaying Song title
			String songTitle = mItemBundle.getString("songTitle");
			songTitleLabel.get().setText(songTitle);

			DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.build();
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
			.defaultDisplayImageOptions(defaultOptions).build();
			ImageLoader coverLoader = ImageLoader.getInstance(); 
			coverLoader.init(config);

			String imageUri = mItemBundle.getString("songCover");			
			coverLoader.loadImage(imageUri, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					songCover.get().setImageBitmap(loadedImage);
				}
			});

			// Changing Button Image to pause image
			btnPlay.get().setImageResource(R.drawable.btn_pause);
			// set Progress bar values
			songProgressBar.get().setProgress(0);
			songProgressBar.get().setMax(100);
			// Updating progress bar
			updateProgressBar();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ----------------onSeekBar Change Listener------------------------------//
	/**
	 * Update timer on seekbar
	 * */
	public void updateProgressBar() {
		progressBarHandler.postDelayed(mUpdateTimeTask, 100);
	}

	/**
	 * Background Runnable thread
	 * */
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			long totalDuration = 0;
			try {
				totalDuration = mp.getDuration();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
			long currentDuration = 0;
			try {
				currentDuration = mp.getCurrentPosition();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
			// Displaying Total Duration time
			songTotalDurationLabel.get().setText(
					"" + utils.milliSecondsToTimer(totalDuration));
			// Displaying time completed playing
			songCurrentDurationLabel.get().setText(
					"" + utils.milliSecondsToTimer(currentDuration));

			// Updating progress bar
			int progress = (int) (utils.getProgressPercentage(currentDuration,
					totalDuration));
			// Log.d("Progress", ""+progress);
			songProgressBar.get().setProgress(progress);
			// Running this thread after 100 milliseconds
			progressBarHandler.postDelayed(this, 100);
			// Log.d("AndroidBuildingMusicPlayerActivity","Runable  progressbar");
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnPlay:
			if (mp.isPlaying()) {
				if (mp != null) {
					mp.pause();
					// Changing button image to play button
					btnPlay.get()
					.setImageResource(R.drawable.btn_play);
					Log.d("Player Service", "Pause");

				}
			} else {
				// Resume song
				if (mp != null) {
					mp.start();
					// Changing button image to pause button
					btnPlay.get().setImageResource(
							R.drawable.btn_pause);
					Log.d("Player Service", "Play");
				}
			}

			break;

		case R.id.btnNext:
			// check if next song is there or not
			Log.d("Player Service", "Next");
			//			if (currentSongIndex < (songsListingSD.size() - 1)) {
			//				playSong(currentSongIndex + 1);
			//				currentSongIndex = currentSongIndex + 1;
			//			} else {
			//				// play first song
			//				playSong(0);
			//				currentSongIndex = 0;
			//			}
			break;

		case R.id.btnForward:

			// get current song position
			int currentPosition = mp.getCurrentPosition();
			// check if seekForward time is lesser than song duration
			if (currentPosition + seekForwardTime <= mp.getDuration()) {
				// forward song
				mp.seekTo(currentPosition + seekForwardTime);
			} else {
				// forward to end position
				mp.seekTo(mp.getDuration());
			}
			break;

		case R.id.btnBackward:
			// get current song position
			int currentPosition2 = mp.getCurrentPosition();
			// check if seekBackward time is greater than 0 sec
			if (currentPosition2 - seekBackwardTime >= 0) {
				// forward song
				mp.seekTo(currentPosition2 - seekBackwardTime);
			} else {
				// backward to starting position
				mp.seekTo(0);
			}
			break;

		case R.id.btnPrevious:

			//			if (currentSongIndex > 0) {
			//				playSong(currentSongIndex - 1);
			//				currentSongIndex = currentSongIndex - 1;
			//			} else {
			//				// play last song
			//				playSong(songsListingSD.size() - 1);
			//				currentSongIndex = songsListingSD.size() - 1;
			//			}
			break;

		case R.id.btnRepeat:

			if (isRepeat) {
				isRepeat = false;
				Toast.makeText(getApplicationContext(), "Repeat is OFF",
						Toast.LENGTH_SHORT).show();
				btnRepeat.get().setImageResource(R.drawable.btn_repeat);
			} else {
				// make repeat to true
				isRepeat = true;
				Toast.makeText(getApplicationContext(), "Repeat is ON",
						Toast.LENGTH_SHORT).show();
				// make shuffle to false
				isShuffle = false;
				btnRepeat.get().setImageResource(R.drawable.btn_repeat_focused);
				btnShuffle.get().setImageResource(R.drawable.btn_shuffle);
			}
			break;

		case R.id.btnShuffle:

			if (isShuffle) {
				isShuffle = false;
				Toast.makeText(getApplicationContext(), "Shuffle is OFF",
						Toast.LENGTH_SHORT).show();
				btnShuffle.get().setImageResource(R.drawable.btn_shuffle);
			} else {
				// make repeat to true
				isShuffle = true;
				Toast.makeText(getApplicationContext(), "Shuffle is ON",
						Toast.LENGTH_SHORT).show();
				// make shuffle to false
				isRepeat = false;
				btnShuffle.get().setImageResource(
						R.drawable.btn_shuffle_focused);
				btnRepeat.get().setImageResource(R.drawable.btn_repeat);
			}
			break;
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * When user starts moving the progress handler
	 * */
	public void onStartTrackingTouch(SeekBar seekBar) {
		// remove message Handler from updating progress bar
		progressBarHandler.removeCallbacks(mUpdateTimeTask);
	}

	@Override
	/**
	 * When user stops moving the progress hanlder
	 * */
	public void onStopTrackingTouch(SeekBar seekBar) {
		progressBarHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = mp.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(),
				totalDuration);

		// forward or backward to certain seconds
		mp.seekTo(currentPosition);

		// update timer progress again
		updateProgressBar();
	}

	@Override
	/**
	 * On Song Playing completed if repeat is ON play same song again if shuffle
	 * is ON play random song
	 * */
	public void onCompletion(MediaPlayer arg0) {

		// check for repeat is ON or OFF
//		if (isRepeat) {
//			// repeat is on play same song again
//			//playSong(currentSongIndex);
//		} else if (isShuffle) {
//			// shuffle is on - play a random song
//			Random rand = new Random();
//			currentSongIndex = rand
//					.nextInt((songsListingSD.size() - 1) - 0 + 1) + 0;
//			//playSong(currentSongIndex);
//		} else {
//			// no repeat or shuffle ON - play next song
//			if (currentSongIndex < (songsListingSD.size() - 1)) {
//				//playSong(currentSongIndex + 1);
//				currentSongIndex = currentSongIndex + 1;
//			} else {
//				// play first song
//				//playSong(0);
//				currentSongIndex = 0;
//			}
//		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//currentSongIndex = -1;
		// Remove progress bar update Hanlder callBacks
		progressBarHandler.removeCallbacks(mUpdateTimeTask);
		Log.d("Player Service", "Player Service Stopped");
		if (mp != null) {
			if (mp.isPlaying()) {
				mp.stop();
			}
			mp.release();
		}

	}

}
