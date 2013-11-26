package org.eneim.tempapp.items;


public class CSNMusicPlaylistItem {

	private String url = null;
	private String title = "default";
	private String performer = "default";
	//private String author = "default";
	//private List<String> availableFormat;
	//private String lyric = "default";
	private String coverURL;
	private String listened;
	private String downloaded;
	private String liked;
	private String length;
	private String format;

	public CSNMusicPlaylistItem() {

	}

	public CSNMusicPlaylistItem(String url, String title, String performer, String liked, 
			String listened, String downloaded, String length, String format) {
		this.url = url;
		this.title = title;
		this.performer = performer;
		//this.author = author;
		//this.lyric = lyric;
		this.liked = liked;
		this.listened = listened;
		this.downloaded = downloaded;
		this.format = format;
		this.length = length;
	}

	public String getItemUrl() {
		return url;
	}
	
	public String getTitle() {
		return title;
	}

	public String getPerformer() {
		return performer;
	}

	public String getCoverURL() {
		return coverURL;
	}

	public String getListened() {
		return listened;
	}

	public String getDownloaded() {
		return downloaded;
	}

	public String getLiked() {
		return liked;
	}

	public String getLength() {
		return length;		
	}
	
	public String getFormat() {
		return format;		
	}

}
