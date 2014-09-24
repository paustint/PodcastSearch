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
	
	public GUIconnector(String APIkey) throws Exception{
			dp = new DigitalPodcast(APIkey);
			dp.setFormat(2);
			//parser = new RSSFeedParser(dp.getQuery());
			//feed = parser.readFeed();
			//messages = feed.getMessages();
			//parser = new RSSFeedParser(messages.get(2).getSource());
			//feed = parser.readFeed();
			//messages = feed.getMessages();
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
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	


}
