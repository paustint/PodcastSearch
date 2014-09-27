package net.austinturner.podcast.GUI;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.austinturner.podcast.RSS.RSSFeed;
import net.austinturner.podcast.RSS.RSSFeedMessage;
import net.austinturner.podcast.RSS.RSSFeedParser;
import net.austinturner.podcast.service.DigitalPodcast;

public class GUIconnector {
	private static DigitalPodcast dp;
	private RSSFeedParser parser;
	private RSSFeed feed;
	private List<RSSFeedMessage> messages;
	private final boolean DEBUG = true;
	
	public GUIconnector(String APIkey) throws Exception{
			dp = new DigitalPodcast(APIkey);
			dp.setFormat(2);
	}
	public static DigitalPodcast getActiveDP(){
		return dp;
	}
	
	/**
	 * Get Feed
	 * @return
	 */
	public RSSFeed getFeed(){
		return feed;
	}
	/**
	 * get Messages
	 * @return
	 */
	public List<RSSFeedMessage> getMessages(){
		return messages;
	}
	/**
	 * Overloaded - allow smaller result of messages to be returned if desired<br>
	 * @param start
	 * @param numResults
	 * @return
	 */
	public List<RSSFeedMessage> getMessages(int start, int numResults){
		return messages.subList(start, numResults+1);
	}
	
	
	public void setParameters(String keywords, int numResults, int sort, int contentFilter, int searchsource, int start){
		try {
			dp.setKeyword(keywords);
			dp.setNumResults(numResults);
			dp.setSort(sort);
			dp.setContentFilter(contentFilter);
			dp.setSearchSource(searchsource);
			dp.setStart(start);
			parser = new RSSFeedParser(dp.getQuery());
			feed = parser.readFeed();
			messages = feed.getMessages();
			if (messages.get(0).getLink().equals("http://www.digitalpodcast.com/feeds/featured")) messages.remove(0);
			debug();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is called when the RSS URL is known, without connecting to an API service<br>
	 * @param rssFeedUrl
	 */
	public boolean setNewSearch(String rssFeedUrl){
		try {
			parser = new RSSFeedParser(rssFeedUrl);
			feed = parser.readFeed();
			messages = feed.getMessages();
			if (messages.get(0).getLink().equals("http://www.digitalpodcast.com/feeds/featured")) messages.remove(0);
			debug(rssFeedUrl);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Set start and refresh query results
	 * @param start
	 */
	public void setStart(int start){
		dp.setCurrStart(start);
		try {
			parser = new RSSFeedParser(dp.getQuery());
			feed = parser.readFeed();
			messages = feed.getMessages();
			if (messages.get(0).getLink().equals("http://www.digitalpodcast.com/feeds/featured")) messages.remove(0);
			debug();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void debug(String query){
		if(DEBUG){
			System.out.println(feed);
			System.out.println(query);
			for (RSSFeedMessage m : messages){
				System.out.println(m);
			}
		}
	}
	private void debug(){
			debug(dp.getQuery());
	}
	
	
	
	


}
