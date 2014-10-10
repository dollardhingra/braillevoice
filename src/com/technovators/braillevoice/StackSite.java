package com.technovators.braillevoice;


/*
 * Data object that holds all of our information about a StackExchange Site.
 */
public class StackSite {

	private String title; //name
	private String link;  //link
	private String description; //about
	private String pubDate; //imgURL
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	@Override
	public String toString() {
		return "StackSite [title=" + title + ", link=" + link + ", description="
				+ description + ", pubDate=" + pubDate + "]";
	}
}
