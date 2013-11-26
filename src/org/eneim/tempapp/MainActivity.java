package org.eneim.tempapp;

import org.eneim.tempapp.items.CSNMusicPlaylistItem;
import org.eneim.tempapp.view.MainView;
import org.eneim.tempapp.view.MainView.MainViewInterface;
import org.eneim.tempapp.MusicPlayerActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements MainViewInterface {

	//private FirstView fv;
	private MainView fv;
	//private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fv = new MainView(this);
		setContentView(fv);
		
		fv.setInterface(this);
	}

	@Override
	public void onItemClickLoading(ListView view, int pos) {
		CSNMusicPlaylistItem item = (CSNMusicPlaylistItem) view.getAdapter().getItem(pos);
		
		Toast.makeText(this, view.getId() + "", Toast.LENGTH_SHORT).show();
		
		Intent in = new Intent(this, MusicPlayerActivity.class);
		String itemUrl = item.getItemUrl();				
		Log.d("CSN", itemUrl + "");
		in.putExtra("link", itemUrl);
		this.startActivity(in);
	}

}
