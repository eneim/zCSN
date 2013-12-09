package org.eneim.tempapp.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eneim.tempapp.items.CSNMusicPlaylistItem;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class CSNMusicPlaylistParser {

	private List<CSNMusicPlaylistItem> resultList;
	private CSNMusicPlaylistItem csnMskItem;
	
	private String url = "http://chiasenhac.com";
	
	private String title;
	private String performer;
	private String itemUrl;
	private String listened;
	private String downloaded;
	private String coverURL;
	private String length;
	private String format;
		
	public CSNMusicPlaylistParser(String url) {
		this.url = url;
	}
	
	public List<CSNMusicPlaylistItem> parse() {
		
		resultList = new ArrayList<CSNMusicPlaylistItem>();
		csnMskItem = new CSNMusicPlaylistItem();
		
		final Document doc;		
		final Elements items;
		final Element topMusicItems;
		Element mskItem;
		
		try {
			
			Connection jconnect = Jsoup.connect(url).header("Accept-Encoding", "gzip"); 
			
			doc = jconnect.get();
			topMusicItems = doc.select("div.h-main4 div.h-center").first();
			
			items = topMusicItems.select("div.list-r.list-1");
			Log.d("SIZE", "\t" + items.size());
			
			if (items.size() > 0) {
				for (int i = 0; i < items.size(); i++) {
					mskItem = items.get(i);
					title = mskItem.select("div.text2 a.txtsp1").first().text();
					performer = mskItem.select("div.text2 p.spd1").first().text();
					
					itemUrl = mskItem.select("div.text2 a.txtsp1").first().attr("href"); // link to this item
					
					downloaded = mskItem.select("div.texte3 p.middle").first().text();					
					length = mskItem.select("div.texte2 p").first().text();
					format = mskItem.select("div.texte2 p span").text();	

					coverURL = null;
					listened = null;
					
					csnMskItem = new CSNMusicPlaylistItem(itemUrl, title, performer, coverURL, listened, downloaded, length, format);
					resultList.add(csnMskItem);																					
				}
				
				mskItem = topMusicItems.select("div.list-r.list-2").first();
				title = mskItem.select("div.text2 a.txtsp1").first().text();
				performer = mskItem.select("div.text2 p.spd1").first().text();
				
				itemUrl = mskItem.select("div.text2 a.txtsp1").first().attr("href"); // link to this item
				
				downloaded = mskItem.select("div.texte3 p.middle").first().text();					
				length = mskItem.select("div.texte2 p").first().text();
				format = mskItem.select("div.texte2 p span").text();	

				coverURL = null;
				listened = null;
				
				csnMskItem = new CSNMusicPlaylistItem(itemUrl, title, performer, coverURL, listened, downloaded, length, format);
				resultList.add(csnMskItem);
									
			}
			
			else return resultList;			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultList;
	}

}
