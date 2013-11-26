package org.eneim.tempapp;

import org.eneim.tempapp.items.CSNMusicPlaylistItem;
import org.eneim.tempapp.view.MainView;
import org.eneim.tempapp.view.MainView.SecondViewInterface;
import org.eneim.tempapp.MusicPlayerActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity implements SecondViewInterface {

	//private FirstView fv;
	private MainView fv;
	//private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//fv = new FirstView(this);
		fv = new MainView(this);
		setContentView(fv);
		
		fv.setInterface(this);
	}

	@Override
	public void onItemClickLoading(ListView view, int pos) {
		CSNMusicPlaylistItem item = (CSNMusicPlaylistItem) view.getAdapter().getItem(pos);
		
		Intent in = new Intent(this, MusicPlayerActivity.class);
		String itemUrl = item.getItemUrl();				
		Log.d("CSN", itemUrl + "");
		in.putExtra("link", itemUrl);		
		this.startActivity(in);
	}

}
