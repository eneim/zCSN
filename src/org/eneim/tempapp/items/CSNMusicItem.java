/**
 * 
 */
package org.eneim.tempapp.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author eneim
 *
 */
public class CSNMusicItem {

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

	public CSNMusicItem() {

	}

	public CSNMusicItem(String itemLink, String title, List<String> performer, List<String> author, 
			List<String> availableFormat, String lyric, String coverURL,
			String listened, String downloaded, String uploader, String linkToPlay) {
		this.itemLink = itemLink;
		this.title = title;
		this.performer = performer;
		this.author = author;
		this.availableFormat = availableFormat;
		this.lyric = lyric;
		this.coverURL = coverURL;
		this.listened = listened;
		this.downloaded = downloaded;		
		this.uploader = uploader;
		this.linkToPlay = linkToPlay;
	}

	public String getItemLink() { return itemLink; }

	public String getID() { return id; }

	public String getTitle() { return title; }

	public List<String> getPerformer() { return performer; }

	public List<String> getAuthor() { return author; }

	public List<String> getAvailableFormat() { return availableFormat; }

	public String getLyric() { return lyric; }

	public String getCoverURL() { return coverURL; }

	public String getListened() { return listened; }

	public String getDownloaded() { return downloaded; }

	public String getLength() { return length; }

	public String getUploader() { return uploader; }

	public String getLinkToPlay() { return linkToPlay; }
	
	public List<String> getDownloadLink() { return downloadLink; }

	public void setItemLink(String link) {
		this.itemLink = link;		
	}

	public void setId() {
		String link = this.getItemLink();
		String[] toker = link.split("~");
		int i;

		String tempId = null;
		if ((i = toker.length) > 0) {
			tempId = toker[i - 1];
			tempId = FilenameUtils.removeExtension(tempId);
		}

		this.id = tempId;
	}

	public String setId(String link) {
		String[] toker = link.split("~");
		int i;
		String tempId = null;
		if ((i = toker.length) > 0) {
			tempId = toker[i - 1];
			tempId = FilenameUtils.removeExtension(tempId);			
		}

		return tempId;		 
	}

	public void setDownloadLink() {
		final String link = this.getItemLink();
		List<String> dlLink = new ArrayList<String>();

		Document doc;
		Elements mskItems;

		String id = setId(link);
		String downloadPage = "http://chiasenhac.com/download.php?m=" + id;
		Connection jconnect = Jsoup.connect(downloadPage);
		try {
			doc = jconnect.get();
			String t;
			mskItems = doc.select("DIV#downloadlink a");
			if (mskItems != null)
				for (int i = 0; i < mskItems.size(); i++) {
					t = mskItems.get(i).attr("href");
					if (t.length() >= 4) {						
						dlLink.add(t);						
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.downloadLink = dlLink;
	}
}