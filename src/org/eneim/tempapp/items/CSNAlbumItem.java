package org.eneim.tempapp.items;

import java.util.List;

public class CSNAlbumItem {

	private String title;
	private String coverURL;
	private List<String> artists;
	private List<String> albumItems;
	
	private String albumLink;
	
	public CSNAlbumItem() {
		
	}
	
	public CSNAlbumItem(String title, String coverURL, List<String> artists) {
		this.title = title;
		this.coverURL = coverURL;
		this.artists = artists;
		//this.albumItems = albumItems;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setAlbumLink(String albumLink) {
		this.albumLink = albumLink;
	}
	
	public void setAlbumCover(String coverURL) {
		this.coverURL = coverURL;
	}
	
}
