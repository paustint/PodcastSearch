/**
 * @author Austin
 * reference:<br>
 * http://www.vogella.com/tutorials/RSSFeed/article.html
 */
package net.austinturner.podcast.RSS;

import java.util.ArrayList;
import java.util.List;

public class RSSFeed {
	
	private String title = "title";
	private String link = "link";
	private String description = "description";
	private String pubDate = "pubDate";
	private String language = "language";
	private String image = "image";
	
	
	//Still need to figure these out (only exist on main API search, maybe have flag or alternate constructor)
	private String totalResults = "podcastSearch:totalResults";
	private String startIndex = "podcastSearch:startIndex";
	private String itemsPerPage = "podcastSearch:itemsPerPage";
	
	final List<RSSFeedMessage> entries = new ArrayList<RSSFeedMessage>();
	
	/**
	 * 
	 * @param title
	 * @param link
	 * @param description
	 * @param pubDate
	 * @param language
	 */
	public RSSFeed(String title, String link, String description, String pubDate, String language, String image){
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
		this.language = language;
		this.image = image;

	}
	public List<RSSFeedMessage> getMessages(){
		return entries;
	}
	
	public String getTitle(){
		return title;
	}
	public String getLink(){
		return link;
	}
	public String getDescription(){
		return description;
	}
	public String getPubDate(){
		return pubDate;
	}
	public String getLanguage() {
		return language;
	}
	public String getImage(){
		return image;
	}
	public String toString(){
		return "Feed [title=" + title + ", link=" + link + ", description=" + description + ", pubdate=" 
	+ pubDate + ", language=" + language + ", image="+ image +"]";
	}
	//Getters and setters for feed info.  This is only used on initial API search, and all queries do not have page numbers
	public String getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(String totalResults) {
		this.totalResults = totalResults;
	}
	public String getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(String startIndex) {
		this.startIndex = startIndex;
	}
	public String getItemsPerPage() {
		return itemsPerPage;
	}
	public void setItemsPerPage(String itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	
}
