package org.eneim.tempapp.fragments;

import java.util.ArrayList;
import java.util.List;

import org.eneim.tempapp.MusicPlayerActivity;
import org.eneim.tempapp.R;
import org.eneim.tempapp.items.CSNMusicPlaylistItem;
import org.eneim.tempapp.parser.CSNMusicPlaylistParser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class DataListFragment3 extends ListFragment implements LoaderManager.LoaderCallbacks<List<CSNMusicPlaylistItem>> {

	public String mURL;
	public Context mContext;
		
	private List<CSNMusicPlaylistItem> mCSNMusicItemList;
	//private CSNMusicPlaylistParser mMusicItemProcessor;
	private CSNMusicAdapter mCSNMusicAdapter;
	
	private ListView list;
	
	public DataListFragment3(Context context, String source) {
		mURL = source;
		mContext = context;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		list = (ListView) inflater.inflate(R.layout.list, null);
		mCSNMusicItemList = new ArrayList<CSNMusicPlaylistItem>();
		mCSNMusicAdapter = new CSNMusicAdapter(mContext, R.layout.item_music, 
				mCSNMusicItemList);
		
		return list;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//new DoCSNMusicTask().execute(mURL);
//		mCSNMusicAdapter = new CSNMusicAdapter(mContext, R.layout.item_music, 
//				mCSNMusicItemList);
		setListAdapter(mCSNMusicAdapter);
		//setListShown(false);
		getLoaderManager().initLoader(0, null, this);
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

			csnMusicItemHolder.setColor(getResources().getColor(R.color.Black));
			//String _coverURL = mskItem.getCoverURL();
			//UrlImageViewHelper.setUrlDrawable(csnMusicItemHolder.mskCoverView, _coverURL);
			//csnMusicItemHolder.mskCoverView.setBackgroundResource(R.drawable.ic_launcher);

			return view;
		}
				
		public void setData(List<CSNMusicPlaylistItem> data) {
	        clear();
	        if (data != null) {
	            for (CSNMusicPlaylistItem appEntry : data) {
	                add(appEntry);
	            }
	        }
	    }
	} 

	static class CSNMusicItemViewHolder {
		public TextView mskTitleView; 
		public TextView mskPerfomerView;         
		public TextView mskLengthView; 
		public TextView mskDownloadedView; 
		public TextView mskFormatView;
		
		public void setColor(int color) {
			mskDownloadedView.setTextColor(color);
			mskFormatView.setTextColor(color);
			mskLengthView.setTextColor(color);
			mskPerfomerView.setTextColor(color);
			mskTitleView.setTextColor(color);
		}		
	}

	public static class DataListLoader extends AsyncTaskLoader<List<CSNMusicPlaylistItem>> {

		private String _URL;
		private List<CSNMusicPlaylistItem> mItemList;
		private CSNMusicPlaylistParser mMusicItemProcessor;
		
		public DataListLoader(Context context, String url) {
			super(context);			
			// TODO Auto-generated constructor stub
			_URL = url;
		}

		@Override
		public List<CSNMusicPlaylistItem> loadInBackground() {
			// TODO Auto-generated method stub
			mMusicItemProcessor = new CSNMusicPlaylistParser(_URL);
			mItemList = mMusicItemProcessor.parse();
			
			return mItemList;
		}
		
		@Override
		public void deliverResult(List<CSNMusicPlaylistItem> data) {
			if (isReset()) {
				// an async query come in while the loader is stopped.
				// we don't need the result;
				if (data != null) {
					onReleaseResources(data);
				}
			}
			
			List<CSNMusicPlaylistItem> oldData = data;
			mItemList = data;
			
			if (isStarted()) {
				// if the loader is currently started, we can immediately
				// deliver its results.
				super.deliverResult(data);
			}
			
			if (oldData != null) {
				onReleaseResources(oldData);
			}
		}
		
		/**
         * Handles a request to start the Loader.
         */
		@Override
		protected void onStartLoading() {
			if (mItemList != null) {
				// if we currently have a result available, deliver it
				// immediately				
				deliverResult(mItemList);
			}
			
			if (takeContentChanged() || mItemList == null) {
				// if the data has changed since the last time it was loaded
				// or is not currently available, start a load
				forceLoad();
			}
		}
		
		/**
		 * Handles a request to stop the loader
		 */
		
		@Override
		protected void onStopLoading() {
			cancelLoad();
		}
		
		/**
		 * Handles a request to cancel a load
		 */
		@Override
		public void onCanceled(List<CSNMusicPlaylistItem> data) {
			// TODO Auto-generated method stub
			super.onCanceled(data);
			
			// at this point, we can release the resources associated with 'data'
			// if needed
			onReleaseResources(data);
		}
		
		/**
		 * Handler a request to completely reset the loader		 * 
		 */
		@Override
		protected void onReset() {
			// TODO Auto-generated method stub
			super.onReset();
			// ensure the loader is stopped
			onStopLoading();
			
			// at this point, we can release the resources associated with "data"
			// if needed			
			if (mItemList != null) {
				onReleaseResources(mItemList);
				mItemList = null;
			}
		}
		
		/**
         * Helper function to take care of releasing resources associated
         * with an actively loaded data set.
         */
        protected void onReleaseResources(List<CSNMusicPlaylistItem> data) {
        	
        }
	}

	@Override
	public Loader<List<CSNMusicPlaylistItem>> onCreateLoader(int arg0,	Bundle arg1) {
		// TODO Auto-generated method stub
		return new DataListLoader(getActivity(), mURL);
	}

	@Override
	public void onLoadFinished(Loader<List<CSNMusicPlaylistItem>> arg0,
			List<CSNMusicPlaylistItem> arg1) {
		// TODO Auto-generated method stub
		mCSNMusicAdapter.setData(arg1);		
	}

	@Override
	public void onLoaderReset(Loader<List<CSNMusicPlaylistItem>> arg0) {
		// TODO Auto-generated method stub
		mCSNMusicAdapter.setData(null);		
	}


}
