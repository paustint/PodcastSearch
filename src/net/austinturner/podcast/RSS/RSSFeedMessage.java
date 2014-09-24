/**
 * This class represents one RSS message
 * @author Austin
 */
package net.austinturner.podcast.RSS;

public class RSSFeedMessage {

	private String title;
	private String description;
	private String link;
	private String source;
	private String comments;
	private String pubDate;
	private String guid;
	private String author;
	private String enclosure; // Still need to figure out how to get attributes out after enclosure 
	private String url;
	private String length;
	private String type;
	//<enclosure url="http://www.controlledburnbbq.com/podcast/episode_05.mp3" length="3894544" type="audio/mpeg" />
	
	@Override
	public String toString(){
		return "FeedMessage [title=" + title + 
				", description=" + description + 
				", link=" + link + 
				", source=" + source +
				", comments=" + comments + 
				", pubDate=" + pubDate + 
				", guid=" + guid + 
				", author=" + author + 
				", url=" + url + 
				", length=" + length + 
				", type=" + type + 
				"]";
	}
	
	public String getMessageText(){
		return  "<html><br><b><u>Title:</b></u> " + title + "<br>" +
				"<b><u>Description:</b></u> " + returnWithBr(description) + "<br>" +
				"<b><u>Release Date:</b></u> " + pubDate +"<br><br></html>";
				
	}
	
	/**
	 * Loop through and add <br> around every 80 characters.<br>
	 * Method looks for " " (space) character or . for new line<br>
	 * @param s
	 * @return
	 */
	private static String returnWithBr(String s){
		try{
			if (s.length()>300){
				s = s.substring(0, 300) + "...";
			}
			String currString = s;
			String newString = "";
			boolean keepLooping = true;
			
				while (keepLooping){
					if (currString.length() >= 81){
						int x1 = 80;
						int x2 = 81;
						String temp = currString.substring(x1, x2);
						//Only add <br> on space or .
						while (!temp.equals(" ") && !temp.equals(".") && !temp.equals("\n") && !temp.equals("\r")){
							x1 ++;
							x2 ++;
							temp = currString.substring(x1, x2);
							
						}
							if (x2 >= currString.length()){
								newString += currString;
								break;
							
							}
					
						//Get substring with line break
						newString += currString.substring(0, x2) +"<br>";
						//Set currString with new string portion of s
						currString = currString.substring(x2);
						//reset counter
						//x1 = 80;
						//x2 = 81;
						
					}
				
				//String is less than 80 characters, so prep return string
				else{
					newString += currString;
					keepLooping = false;
				}
				}
			return newString;
		} catch(StringIndexOutOfBoundsException e){
				return s;		
		}
		
		
	}
	
	
	/////Getter and setters
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getEnclosure() {
		return enclosure;
	}
	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
