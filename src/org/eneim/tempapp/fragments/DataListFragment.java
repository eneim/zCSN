package org.eneim.tempapp.fragments;

import java.util.ArrayList;
import java.util.List;

import org.eneim.tempapp.R;
import org.eneim.tempapp.items.CSNMusicPlaylistItem;
import org.eneim.tempapp.parser.CSNMusicPlaylistParser;
import org.eneim.tempapp.view.MainView;
import org.eneim.tempapp.view.MainView.MainViewInterface;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("ValidFragment")
public class DataListFragment extends ListFragment implements OnItemClickListener {

	public String mURL;
	public Context mContext;
		
	private List<CSNMusicPlaylistItem> mCSNMusicItemList;
	private CSNMusicPlaylistParser mMusicItemProcessor;
	private CSNMusicAdapter mCSNMusicAdapter;
	
	private ListView list;
	
	public DataListFragment(Context context, String source) {
		mURL = source;
		mContext = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		list = (ListView) inflater.inflate(R.layout.list, null);
		mCSNMusicItemList = new ArrayList<CSNMusicPlaylistItem>();
		
		list.setOnItemClickListener(this);
		
		return list;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
//		SampleAdapter adapter = new SampleAdapter(getActivity());
//		for (int i = 0; i < 20; i++) {
//			adapter.add(new SampleItem("Sample List", android.R.drawable.ic_menu_search));
//		}
//		setListAdapter(adapter);
		
		//list = (ListView) findViewById(R.id.listView1);

		//mCSNMusicItemList = new ArrayList<CSNMusicPlaylistItem>();
		new DoCSNMusicTask().execute(mURL);
	}
	
//	private class SampleItem {
//		public String tag;
//		public int iconRes;
//		public SampleItem(String tag, int iconRes) {
//			this.tag = tag; 
//			this.iconRes = iconRes;
//		}
//	}

//	public class SampleAdapter extends ArrayAdapter<SampleItem> {
//
//		public SampleAdapter(Context context) {
//			super(context, 0);
//		}
//
//		public View getView(int position, View convertView, ViewGroup parent) {
//			if (convertView == null) {
//				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
//			}
//			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
//			icon.setImageResource(getItem(position).iconRes);
//			TextView title = (TextView) convertView.findViewById(R.id.row_title);
//			title.setText(getItem(position).tag);
//
//			return convertView;
//		}
//
//	}

	public static interface MainViewInterface {
		public void onItemClickLoading(ListView lv, int pos);
		
		public void onItemClickCustomListener(List<CSNMusicPlaylistItem> itemList, int pos);
		
		public void onItemClickLoading(AdapterView<?> arg0, View view, int pos, long arg3);
	}

	private MainViewInterface mInterface;

	public void setInterface(MainViewInterface inf) {
		mInterface = inf;
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
			prog = new ProgressDialog(getActivity()); 
			prog.setMessage("Loading...."); 
			prog.show();
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
			prog.dismiss(); 
			mCSNMusicAdapter = new CSNMusicAdapter(mContext, R.layout.item_music, 
					mCSNMusicItemList); 
			list.setAdapter(mCSNMusicAdapter);
		}

		@Override
		protected void onProgressUpdate(Void... values) { 

		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		mInterface.onItemClickCustomListener(mCSNMusicItemList, arg2);
	}

//	@Override
//	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
//		// TODO Auto-generated method stub
//		
//		//mInterface.onItemClickLoading(list, pos);
//		mInterface.onItemClickCustomListener(mCSNMusicItemList, pos);
//	}
}
