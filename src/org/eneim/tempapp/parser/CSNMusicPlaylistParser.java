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
			
			long start = System.nanoTime();
			Connection jconnect = Jsoup.connect(url); 
			long end = System.nanoTime();
			Log.d("TIME", "connect:\t" + Long.toString((end - start)/1000000) + "miliseconds");
			
			start = System.nanoTime();
			doc = jconnect.get();
			end = System.nanoTime();			
			Log.d("TIME", "get:\t" + Long.toString((end - start)/1000000) + "miliseconds");
			
			start = System.nanoTime();
			topMusicItems = doc.select("div.h-main4 div.h-center").first();
			
			items = topMusicItems.select("div.list-r.list-1"); // list nhac vietnam
			end = System.nanoTime();			
			Log.d("SIZE", "\t" + items.size());
			Log.d("TIME", "first select:\t" + Long.toString((end - start)/1000000) + "miliseconds");			
			
			if (items.size() > 0) {
				for (int i = 0; i < items.size(); i++) {
					start = System.nanoTime();
					
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
					
					end = System.nanoTime();
					Log.d("TIME", "for " + (i + 1) + "th item:\t" + Long.toString((end - start)/1000000) + "miliseconds");																
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
				
				end = System.nanoTime();			
				Log.d("TIME", "for 10th item:\t" + Long.toString((end - start)/1000000) + "miliseconds");	
			}
			
			else return resultList;			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultList;
	}

}
