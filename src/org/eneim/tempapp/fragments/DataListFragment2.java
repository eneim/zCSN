package org.eneim.tempapp.fragments;

import java.util.ArrayList;
import java.util.List;

import org.eneim.tempapp.MusicPlayerActivity;
import org.eneim.tempapp.R;
import org.eneim.tempapp.items.CSNMusicPlaylistItem;
import org.eneim.tempapp.parser.CSNMusicPlaylistParser;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class DataListFragment2 extends ListFragment {

	public String mURL;
	public Context mContext;
		
	private List<CSNMusicPlaylistItem> mCSNMusicItemList;
	private CSNMusicPlaylistParser mMusicItemProcessor;
	private CSNMusicAdapter mCSNMusicAdapter;
	
	private ListView list;
	
	public DataListFragment2(Context context, String source) {
		mURL = source;
		mContext = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		list = (ListView) inflater.inflate(R.layout.list, null);
		mCSNMusicItemList = new ArrayList<CSNMusicPlaylistItem>();
		
		return list;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		new DoCSNMusicTask().execute(mURL);		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		Intent in = new Intent(getActivity(), MusicPlayerActivity.class);		
		List<String> itemLinksList = new ArrayList<String>();

		if (mCSNMusicItemList == null)
			return;

		CSNMusicPlaylistItem item = mCSNMusicItemList.get(position);
		
		for (int i = 0; i < mCSNMusicItemList.size(); i++) {
			itemLinksList.add(mCSNMusicItemList.get(i).getItemUrl());
		}

		String itemUrl = item.getItemUrl();				
		Log.d("CSN", itemUrl + "");
		in.putExtra("itemLinkList", itemLinksList.toArray(new String[itemLinksList.size()]));
		in.putExtra("mIndex", position);
		this.startActivity(in);
		
		super.onListItemClick(l, v, position, id);
	}
	
	private class CSNMusicAdapter extends ArrayAdapter<CSNMusicPlaylistItem> { 
		private List<CSNMusicPlaylistItem> CSNMusicItemList;  

		public CSNMusicAdapter(Context context, int textViewResourceId, List<CSNMusicPlaylistItem> CSNMusicItemList) { 
			super(context, textViewResourceId, CSNMusicItemList); 
			this.CSNMusicItemList = CSNMusicItemList; 
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) { 

			View view = convertView; 
			CSNMusicItemViewHolder csnMusicItemHolder = null; 
			if (convertView == null) {
				view = View.inflate(mContext, R.layout.item_music, null); 
				csnMusicItemHolder = new CSNMusicItemViewHolder();
				csnMusicItemHolder.mskTitleView = (TextView) view.findViewById(R.id.msk_item_title);
				csnMusicItemHolder.mskPerfomerView = (TextView) view.findViewById(R.id.msk_item_performer); 
				csnMusicItemHolder.mskLengthView = (TextView) view.findViewById(R.id.msk_length);
				csnMusicItemHolder.mskDownloadedView = (TextView) view.findViewById(R.id.msk_item_downloaded);
				csnMusicItemHolder.mskFormatView = (TextView) view.findViewById(R.id.msk_format);
				
				view.setTag(csnMusicItemHolder); 
			} else {
				csnMusicItemHolder = (CSNMusicItemViewHolder) view.getTag();             	
			}

			CSNMusicPlaylistItem mskItem = CSNMusicItemList.get(position); 
			csnMusicItemHolder.mskTitleView.setText(mskItem.getTitle());
			csnMusicItemHolder.mskPerfomerView.setText(mskItem.getPerformer()); 
			csnMusicItemHolder.mskLengthView.setText(mskItem.getLength()); 
			csnMusicItemHolder.mskDownloadedView.setText(mskItem.getDownloaded());
			csnMusicItemHolder.mskFormatView.setText(mskItem.getFormat());

			//String _coverURL = mskItem.getCoverURL();
			//UrlImageViewHelper.setUrlDrawable(csnMusicItemHolder.mskCoverView, _coverURL);
			//csnMusicItemHolder.mskCoverView.setBackgroundResource(R.drawable.ic_launcher);

			return view;
		}
		
		public List<CSNMusicPlaylistItem> getItems() {
			// TODO Auto-generated method stub
			return mCSNMusicItemList;
		}
	} 

	static class CSNMusicItemViewHolder {
		public TextView mskTitleView; 
		public TextView mskPerfomerView;         
		public TextView mskLengthView; 
		public TextView mskDownloadedView; 
		public TextView mskFormatView;
	}
	
	public class DoCSNMusicTask extends AsyncTask<String, Void, List<CSNMusicPlaylistItem>> {
		ProgressDialog prog;
		Handler innerHandler; 

		@Override
		protected void onPreExecute() { 
//			prog = new ProgressDialog(getActivity()); 
//			prog.setMessage("Loading...."); 
//			prog.show();
		}

		@Override
		protected List<CSNMusicPlaylistItem> doInBackground(String... params) {
			for (String urlVal : params) { 
				mMusicItemProcessor = new CSNMusicPlaylistParser(urlVal);
				mCSNMusicItemList = mMusicItemProcessor.parse();
			}

			return mCSNMusicItemList; 
		}

		@Override
		protected void onPostExecute(List<CSNMusicPlaylistItem> result) {
			//prog.dismiss(); 
			mCSNMusicAdapter = new CSNMusicAdapter(mContext, R.layout.item_music, 
					mCSNMusicItemList);
			list.setAdapter(mCSNMusicAdapter);
		}

		@Override
		protected void onProgressUpdate(Void... values) { 

		}
	}
}
