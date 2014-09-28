/**
 * @author Austin
 * reference:<br>
 * http://www.vogella.com/tutorials/RSSFeed/article.html
 */
package net.austinturner.podcast.RSS;

import java.util.ArrayList;
import java.util.List;

public class RSSFeed {
	
	private String title;
	private String link;
	private String description;
	private String pubDate;
	private String language;
	private String image;
	private String author;
	private String copyright;
	private String subtitle;
	private String summary;
	private String name;
	private String email;
	private String category;
	private String folderName = "";
	private String totalResults = "";
	private String startIndex = "";
	private String itemsPerPage = "";
	
	final List<RSSFeedMessage> entries = new ArrayList<RSSFeedMessage>();
	
	final boolean DEBUG = true; 
	
	/**
	 * 
	 * @param title
	 * @param link
	 * @param description
	 * @param pubDate
	 * @param language
	 */
	public RSSFeed(String title, String link, String description, String pubDate, String language, String image,
			String author, String copyright, String subtitle, String summary, String name, String email, String category){
		this.title = title;
		this.link = link;
		this.description = description;
		this.pubDate = pubDate;
		this.language = language;
		this.image = image;
		this.author = author;
		this.copyright = copyright;
		this.subtitle = subtitle;
		this.summary = summary;
		this.name = name;
		this.email = email;
		this.category = category;
		if(DEBUG){
			System.out.println("title: " + title);
			System.out.println("link: " + link);
			System.out.println("description: " + description);
			System.out.println("pubDate: " + pubDate);
			System.out.println("language: " + language);
			System.out.println("image: " + image);
			System.out.println("author: " + author);
			System.out.println("copyright: " + copyright);
			System.out.println("subtitle: " + subtitle);
			System.out.println("summary: " + summary);
			System.out.println("name: " + name);
			System.out.println("email: " + email);
			System.out.println("category: " + category);
		}
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
		return "Feed [" + 
				"title=" + title +
				", link=" + link +
				", description=" + description +
				", pubDate=" + pubDate +
				", language=" + language +
				", image=" + image +
				", author=" + author +
				", copyright=" + copyright +
				", subtitle=" + subtitle +
				", summary=" + summary +
				", name=" + name +
				", email=" + email +
				", category=" + category +
				"]";
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getFolderName() {
		//Make sure foldername is set, and set to title if it is not set
		if (folderName.equals("")){
			folderName = title;
		}
		return folderName;
	}
	public void setFolderName(String folderName) {
		String f = folderName.trim();
		f = folderName.replaceAll("[^a-zA-Z0-9_.!-]", "_");
		if(DEBUG) System.out.println(f);
		this.folderName = f;
	}

	
}
