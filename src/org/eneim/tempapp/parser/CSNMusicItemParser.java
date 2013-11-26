package org.eneim.tempapp.parser;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.eneim.tempapp.items.CSNMusicItem;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CSNMusicItemParser {

	private String itemLink;
	private String id;
	private String title;
	private List<String> performer;
	private List<String> author;
	private List<String> availableFormat;
	private String lyric;
	private String coverURL;
	private String listened;
	private String downloaded;
	private String length;
	private List<String> downloadLink;
	private String uploader;
	private String linkToPlay;

	public CSNMusicItem parse(String itemLink) {		

		CSNMusicItem temp = new CSNMusicItem();

		final Document doc;		
		final Elements items;
		Elements mskItems;
		Element mskItem;

		try {
			Connection jconnect = Jsoup.connect(itemLink).timeout(5000)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.69 Safari/537.36");			
			doc = jconnect.get();

			items = doc.select("DIV.tip-text").first().select("P");

			title = items.first().select("SPAN.maintitle").select("a").text();	// title

			linkToPlay = null;

			Elements tags = doc.select("script");

			String temp1 = tags.html();

			String[] raw = temp1.split(",");
			
			for (String i : raw) {
				if (i.contains("file")) {
					linkToPlay = URLDecoder.decode(i.split("\"")[3], "UTF-8");
					break;
				}
			}

			mskItems = items.select("b");

			Elements performers = mskItems.first().select("a"); 
			performer = new ArrayList<String>();

			String t;
			int size;

			if (performers != null)
				for (int i = 0; i < performers.size(); i++) {
					t = performers.get(i).text();
				}

			Elements authors = mskItems.get(1).select("a");
			author = new ArrayList<String>();

			if (authors != null)
				for (int i = 0; i < authors.size(); i++) {
					t = authors.get(i).text();
					author.add(t);
				}

			Elements formats = doc.select("DIV.gen").first().select("img");
			availableFormat = new ArrayList<String>();

			if (formats != null) {
				for (int i = 0; i < formats.size(); i++) {
					t = formats.get(i).attr("alt");
					availableFormat.add(t);
				}
			}			

			size = items.size();			
			lyric = items.get(size - 1).select("P.genmed").text(); // or .html()			

			mskItem = doc.select("DIV#fulllyric").first().select("img").first();
			coverURL = mskItem.attr("src");

			mskItems = doc.select("DIV.datelast SPAN");
			uploader = mskItems.first().text();
			listened = mskItems.get(1).text();
			downloaded = mskItems.get(2).text();

			temp = new CSNMusicItem(itemLink, title, performer, author, availableFormat, 
					lyric, coverURL, listened, downloaded, uploader, linkToPlay);
			temp.setId();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return temp;
	}
}
