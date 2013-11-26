package org.eneim.tempapp.view;

import java.util.ArrayList;
import java.util.List;

import org.eneim.tempapp.R;
import org.eneim.tempapp.items.CSNMusicPlaylistItem;
import org.eneim.tempapp.parser.CSNMusicPlaylistParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainView extends BaseLayout implements OnItemClickListener {

	private List<CSNMusicPlaylistItem> mCSNMusicItemList;
	private CSNMusicPlaylistParser mMusicItemProcessor;
	private CSNMusicAdapter mCSNMusicAdapter;

	private Button bt;
	private ListView list;

	private static final String csnURL = "http://chiasenhac.com/mp3/vietnam/";

	public MainView(Context context) {
		super(context, R.layout.second_view);
		list = (ListView) findViewById(R.id.listView1);

		mCSNMusicItemList = new ArrayList<CSNMusicPlaylistItem>();
		new DoCSNMusicTask().execute(csnURL);

		//list.setOnItemClickListener(this);
		list.setOnItemClickListener(this);
	}
	
	public static interface MainViewInterface {
		public void onItemClickLoading(ListView lv, int pos);
	}

	private MainViewInterface mInterface;

	public MainView setInterface(MainViewInterface inf) {
		mInterface = inf;
		return this;
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
			CSNMusicItemHolder csnMusicItemHolder = null; 
			if (convertView == null) {
				view = View.inflate(mContext, R.layout.item_music, null); 
				csnMusicItemHolder = new CSNMusicItemHolder();
				csnMusicItemHolder.mskTitleView = (TextView) view.findViewById(R.id.msk_item_title);
				csnMusicItemHolder.mskPerfomerView = (TextView) view.findViewById(R.id.msk_item_performer); 
				csnMusicItemHolder.mskLengthView = (TextView) view.findViewById(R.id.msk_length);
				csnMusicItemHolder.mskDownloadedView = (TextView) view.findViewById(R.id.msk_item_downloaded);
				csnMusicItemHolder.mskFormatView = (TextView) view.findViewById(R.id.msk_format);
				
				view.setTag(csnMusicItemHolder); 
			} else {
				csnMusicItemHolder = (CSNMusicItemHolder) view.getTag();             	
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
	} 

	static class CSNMusicItemHolder {
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
			prog = new ProgressDialog(mContext); 
			prog.setMessage("Loading...."); 
			prog.show();
		}

		@Override
		protected List<CSNMusicPlaylistItem> doInBackground(String... params) {
			for (String urlVal : params) { 
				Log.d("NAM", urlVal);	
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
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		// TODO Auto-generated method stub
		
		mInterface.onItemClickLoading(list, pos);		
	}
}
