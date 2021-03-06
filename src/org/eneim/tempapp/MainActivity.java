package org.eneim.tempapp;

import java.util.ArrayList;
import java.util.List;

import org.eneim.tempapp.items.CSNMusicPlaylistItem;
import org.eneim.tempapp.services.MusicPlayerService;
import org.eneim.tempapp.view.MainView;
import org.eneim.tempapp.view.MainView.MainViewInterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
		
	}

	@Override
	public void onItemClickLoading(AdapterView<?> parent, View view, int pos,
			long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClickCustomListener(List<CSNMusicPlaylistItem> itemList, int pos) {
		// TODO Auto-generated method stub
		Intent in = new Intent(this, MusicPlayerActivity.class);		
		List<String> itemLinksList = new ArrayList<String>();

		if (itemList == null)
			return;

		CSNMusicPlaylistItem item = itemList.get(pos);
		
		for (int i = 0; i < itemList.size(); i++) {
			itemLinksList.add(itemList.get(i).getItemUrl());
		}

		String itemUrl = item.getItemUrl();				
		Log.d("CSN", itemUrl + "");
		in.putExtra("itemLinkList", itemLinksList.toArray(new String[itemLinksList.size()]));
		in.putExtra("mIndex", pos);
		this.startActivity(in);

	}

}
