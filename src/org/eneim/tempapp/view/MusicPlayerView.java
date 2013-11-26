package org.eneim.tempapp.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.eneim.tempapp.R;
import org.eneim.tempapp.items.CSNMusicItem;
import org.eneim.tempapp.parser.CSNMusicItemParser;
import org.eneim.tempapp.utilities.Utilities;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MusicPlayerView extends BaseLayout implements OnClickListener {
	public Context mContext;
	public static CSNMusicItem mItem;
	// Media Player
	public Handler mHandler = new Handler();
	//public SongsManager songManager;
	public Utilities utils;

	public String itemLink;
	public static String linkToPlay;

	public CSNMusicItemParser parser;

	public static ImageButton btnPlay;
	public static ImageButton btnForward;
	public static ImageButton btnBackward;
	public static ImageButton btnNext;
	public static ImageButton btnPrevious;
	public static ImageButton btnPlaylist;
	public static ImageButton btnRepeat;
	public static ImageButton btnShuffle;
	public static ImageView songCover;
	public static SeekBar songProgressBar;
	public static TextView songTitleLabel;
	public static TextView songCurrentDurationLabel;
	public static TextView songTotalDurationLabel;

	public int seekForwardTime = 5000; // 5000 milliseconds
	public int seekBackwardTime = 5000; // 5000 milliseconds
	public int currentSongIndex = 0; 
	public boolean isShuffle = false;
	public boolean isRepeat = false;
	
	public static ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

	public MusicPlayerView(Context context) {
		super(context, R.layout.player);
		// TODO Auto-generated constructor stub
		mContext = context;
		initView(mContext);
	}

	public void initView(Context context) {
		mContext = context;
		// All player buttons
		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		btnForward = (ImageButton) findViewById(R.id.btnForward);
		btnBackward = (ImageButton) findViewById(R.id.btnBackward);
		btnNext = (ImageButton) findViewById(R.id.btnNext);
		btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
		btnPlaylist = (ImageButton) findViewById(R.id.btnPlaylist);
		btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
		btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
		songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
		songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
		songTotalDurationLabel = (TextView) findViewById(R.id.songTotalDurationLabel);
		songTitleLabel = (TextView) findViewById(R.id.songTitle);
		songCover = (ImageView) findViewById(R.id.item_cover);		
		
		btnPlay.setOnClickListener(this);
		btnForward.setOnClickListener(this);
		btnBackward.setOnClickListener(this);
		btnNext.setOnClickListener(this);
		btnPrevious.setOnClickListener(this);
		btnShuffle.setOnClickListener(this);
		btnRepeat.setOnClickListener(this);
	}
		
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
